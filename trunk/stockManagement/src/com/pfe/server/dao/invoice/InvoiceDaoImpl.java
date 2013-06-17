package com.pfe.server.dao.invoice;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.shared.model.Invoice;
import com.pfe.shared.model.Supplier;

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
	public List<Invoice> search(int start, int limit) {
		List<Invoice> results = findByCriteria(start, limit, (Criterion[])null);
		return results;
	}

	@Override
	public List<Invoice> searchUnpaid(int start, int limit) {
		
		Criterion criterion = Restrictions.gt("restToPay", new BigDecimal(0));
		return findByCriteria(start, limit, criterion);
		
	}

	@Override
	public long countUnpaid() {
		Criterion criterion = Restrictions.gt("restToPay", new BigDecimal(0));
		return countByCriteria(criterion);
	}
	
}
