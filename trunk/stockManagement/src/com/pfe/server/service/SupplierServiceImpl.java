package com.pfe.server.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.SupplierService;
import com.pfe.server.dao.supplier.SupplierDao;
import com.pfe.shared.dto.SupplierDto;
import com.pfe.shared.model.Supplier;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierDao dao;
	@Autowired 
	private DozerBeanMapper mapper;

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public List<SupplierDto> getAll() {
		List<Supplier> suppliers = dao.findAll();
		List<SupplierDto> dtos = new ArrayList<SupplierDto>();

		if (suppliers.size() > 0) {
			for (Supplier supplier : suppliers) {
				dtos.add(mapper.map(
						supplier, SupplierDto.class));
			}
		}
		return dtos;
	}

}
