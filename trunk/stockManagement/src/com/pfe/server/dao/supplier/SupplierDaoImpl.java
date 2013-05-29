package com.pfe.server.dao.supplier;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.shared.model.Supplier;

@Repository
public class SupplierDaoImpl extends BaseDaoImpl<Long, Supplier> implements SupplierDao {

	@Autowired
	public SupplierDaoImpl(SessionFactory factory) {
		super.setSessionFactory(factory);
	}
}
