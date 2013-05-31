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
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.SupplierDto;
import com.pfe.shared.model.Supplier;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierDao dao;
	@Autowired 
	private DozerBeanMapper dozerMapper;

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public List<SupplierDto> getAll() {
		List<Supplier> suppliers = dao.findAll();
		List<SupplierDto> dtos = new ArrayList<SupplierDto>();
	
		if (suppliers.size() > 0) {
			for (Supplier supplier : suppliers) {
				dtos.add(dozerMapper.map(
						supplier, SupplierDto.class));
			}
		}
		return dtos;
	}

	@Override
	//@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public PagingLoadResult<SupplierDto> search(FilterPagingLoadConfig config) {
		int size = (int) dao.count();
		int start = config.getOffset();
		int limit = config.getLimit();
		List<Supplier> sublist = dao.search(start, limit, null);
		List<SupplierDto> dtos = new ArrayList<SupplierDto>();
		
		if (sublist.size() > 0) {
			for (Supplier supplier : sublist) {
				dtos.add(dozerMapper.map(
						supplier, SupplierDto.class, "miniSupplier"));
			}
		}	
		return new PagingLoadResultBean<SupplierDto>(dtos, size,config.getOffset());
	}

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public SupplierDto find(Long id) {
		Supplier supplier = dao.get(id);
		if(supplier != null){
			SupplierDto dto = dozerMapper.map(supplier, SupplierDto.class, "fullSupplier");
			return dto;
		}	
		return null;
	}

	@Override
	public SupplierDto create(SupplierDto supplier) throws BusinessException {
		
		//here the supplier has no invoices
		Supplier entity = dozerMapper.map(supplier, Supplier.class, "miniSupplier");
		Supplier merged = dao.merge(entity);
		if(merged != null){
			return dozerMapper.map(merged, SupplierDto.class, "miniSupplier");
		}
		return null;
	}

	@Override
	public SupplierDto update(SupplierDto initial, SupplierDto buffer) throws BusinessException {
		
		//we don't update invoices from the supplier view
		Supplier entity = dozerMapper.map(initial, Supplier.class, "miniSupplier");
		entity.setName(buffer.getName());
		entity.setDescription(buffer.getDescription());
		Supplier merged = dao.merge(entity);
		
		if(merged != null){
			return dozerMapper.map(merged, SupplierDto.class, "miniSupplier");
		}
		
		return null;
	}

}
