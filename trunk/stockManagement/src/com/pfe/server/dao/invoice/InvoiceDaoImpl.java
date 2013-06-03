package com.pfe.server.dao.invoice;

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
		Criterion criterion1 = Restrictions.eq("supplier", supplier);
		List<Invoice> l = findByCriteria(criterion1);
		return l;
	}
	
}
