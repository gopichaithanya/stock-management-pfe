package com.pfe.server.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.SupplierService;
import com.pfe.server.dao.invoice.InvoiceDao;
import com.pfe.server.dao.supplier.SupplierDao;
import com.pfe.server.model.Invoice;
import com.pfe.server.model.Supplier;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	public static final String FULL_SUPPLIER_MAPPING = "fullSupplier";
	public static final String MINI_SUPPLIER_MAPPING = "miniSupplier";

	@Override
	//@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public List<SupplierDTO> getAll() {
		List<Supplier> suppliers = supplierDao.findAll();
		List<SupplierDTO> dtos = new ArrayList<SupplierDTO>();

		if (suppliers.size() > 0) {
			for (Supplier supplier : suppliers) {
				dtos.add(dozerMapper.map(supplier, SupplierDTO.class, MINI_SUPPLIER_MAPPING));
			}
		}
		return dtos;
	}

	@Override
	public PagingLoadResult<SupplierDTO> search(FilterPagingLoadConfig config) {

		//Get name filter value 
		FilterConfig nameFilter = config.getFilters().get(0);
		String filterValue = nameFilter.getValue();	
		if(filterValue != null){
			filterValue = filterValue.trim();
		}
		
		int size = (int) supplierDao.countByCriteria(filterValue);
		int start = config.getOffset();
		int limit = config.getLimit();
		List<Supplier> sublist = supplierDao.search(start, limit, filterValue);
		List<SupplierDTO> dtos = new ArrayList<SupplierDTO>();

		if (sublist.size() > 0) {
			for (Supplier supplier : sublist) {
				dtos.add(dozerMapper.map(supplier, SupplierDTO.class, MINI_SUPPLIER_MAPPING));
			}
		}
		return new PagingLoadResultBean<SupplierDTO>(dtos, size, config.getOffset());
	}

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public SupplierDTO find(Long id) {
		Supplier supplier = supplierDao.get(id);
		if (supplier != null) {
			SupplierDTO dto = dozerMapper.map(supplier, SupplierDTO.class, FULL_SUPPLIER_MAPPING);
			return dto;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public SupplierDTO create(SupplierDTO supplier) throws BusinessException {
		
		Supplier s = supplierDao.search(supplier.getName());
		if (s != null) {
			throw new BusinessException("The name you chose is already in use.");
		}
		
		// here the supplier has no invoices
		Supplier entity = dozerMapper.map(supplier, Supplier.class, MINI_SUPPLIER_MAPPING);
		Supplier merged = supplierDao.merge(entity);
		if (merged != null) {
			return dozerMapper.map(merged, SupplierDTO.class, MINI_SUPPLIER_MAPPING);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public SupplierDTO update(SupplierDTO updatedSupplier) throws BusinessException {
	
		Supplier duplicate = supplierDao.getDuplicateName(updatedSupplier.getId(), updatedSupplier.getName());
		if (duplicate != null) {
			throw new BusinessException("The name you chose is already in use.");
		}

		// we don't update invoices from the supplier view
		Supplier entity = dozerMapper.map(updatedSupplier, Supplier.class, MINI_SUPPLIER_MAPPING);
		Supplier merged = supplierDao.merge(entity);
		if (merged != null) {
			return dozerMapper.map(merged, SupplierDTO.class, MINI_SUPPLIER_MAPPING);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(SupplierDTO supplier) throws BusinessException {

		// don't delete if there are invoices belonging to this supplier
		Supplier entity = dozerMapper.map(supplier, Supplier.class, MINI_SUPPLIER_MAPPING);
		List<Invoice> invoices = invoiceDao.getBySupplier(entity);
		if (invoices.size() > 0) {
			throw new BusinessException( "Supplier has invoices associated and cannot be deleted.");
		} else {
			supplierDao.delete(entity);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(List<SupplierDTO> suppliers) throws BusinessException {
		for(SupplierDTO supplier : suppliers){
			delete(supplier);
		}
		
	}

}
