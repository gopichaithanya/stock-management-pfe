package com.pfe.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.SupplierService;
import com.pfe.server.dao.supplier.SupplierDao;
import com.pfe.shared.model.Supplier;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	private SupplierDao dao;

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public List<Supplier> getAll() {
		List<Supplier> l = dao.findAll();
		List<Supplier> l2 = new ArrayList<Supplier>();
		l2.add(l.get(0));
		return l2;
	}


}
