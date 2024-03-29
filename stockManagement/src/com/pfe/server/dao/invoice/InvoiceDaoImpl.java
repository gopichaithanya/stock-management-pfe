package com.pfe.server.dao.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.server.dao.OrderAlias;
import com.pfe.server.dao.SortField;
import com.pfe.server.model.Invoice;
import com.pfe.server.model.Supplier;

@Repository
public class InvoiceDaoImpl extends BaseDaoImpl<Long, Invoice> implements
		InvoiceDao {
	
	@Autowired
	public InvoiceDaoImpl(SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	@Override
	public List<Invoice> getBySupplier(Supplier supplier) {
		
		Criterion criterion = Restrictions.eq("supplier", supplier);
		return findByCriteria(criterion);
	}

	@Override
	public List<Invoice> search(int start, int limit, Boolean showAll, int code) {
		
		List<SortField> sorts = new ArrayList<SortField>();
		//Retrieve invoices from the most recent to the oldest
		sorts.add(new InvoiceCreatedSortField(/*ascending*/false));
		List<OrderAlias> orderAliases = getOrderAliases(sorts);
		List<Order> orders = getOrders(sorts);

		Criterion allInvoicesCriterion = null;
		Criterion filterCriterion = null;
		
		if(!showAll){
			//Retrieve only unpaid invoices
			allInvoicesCriterion = Restrictions.gt("restToPay", new BigDecimal(0));	
		}
		
		if(code != -1){
			filterCriterion = Restrictions.eq("code", code);
		}
		
		return findByCriteria(start, limit, orderAliases, orders, allInvoicesCriterion, filterCriterion);
	}
	
	@Override
	protected Class<? extends SortField> getSortType() {
		return InvoiceSortField.class;
	}

	@Override
	public long countByCriteria(Boolean countAll, int code) {
		Criterion showAll = null;
		Criterion filter = null;
				
		if(!countAll){
			//Retrieve only unpaid invoices
			showAll = Restrictions.gt("restToPay", new BigDecimal(0));
		}
		
		if(code != -1){
			//Apply code filter
			filter = Restrictions.eq("code", code);
		}
		
		return countByCriteria(showAll, filter);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Invoice> search(int start, int limit, String supplierName) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Invoice.class).createCriteria("supplier");
		Criterion criterion = null;
		
		if(StringUtils.isNotBlank(supplierName)){
			criterion = Restrictions.ilike("name", "%" + supplierName + "%");
		}
		detachedCriteria.add(criterion);
		return (List<Invoice>)getHibernateTemplate().findByCriteria(detachedCriteria, start, limit);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int countByCriteria(Boolean showAll, String supplierName) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Invoice.class).createCriteria("supplier");
		Criterion criterion = null;
		
		if(StringUtils.isNotBlank(supplierName)){
			criterion = Restrictions.ilike("name", "%" + supplierName + "%");
		}
		detachedCriteria.add(criterion);
		List<Invoice> allInvoices = (List<Invoice>)getHibernateTemplate().findByCriteria(detachedCriteria);

		if(!showAll){
			//Keep only those invoices with restToPay > 0
			List<Invoice> unpaidInvoices = new ArrayList<Invoice>();
			for(Invoice invoice : allInvoices){
				if(invoice.getRestToPay().compareTo(new BigDecimal(0)) == 1){
					unpaidInvoices.add(invoice);
				}
			}
			return unpaidInvoices.size();
			
		} else {
			return allInvoices.size();
		}
	}




	
}
