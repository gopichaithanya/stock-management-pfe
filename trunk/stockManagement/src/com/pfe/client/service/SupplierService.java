package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * RPC Service that handles supplier CRUD
 * 
 * @author Alexandra
 * 
 */
@RemoteServiceRelativePath("gxt3/supplierService")
public interface SupplierService extends RemoteService {

	/**
	 * Retrieves all suppliers from database
	 * 
	 * @return
	 */
	public List<SupplierDTO> getAll();

	/**
	 * Retrieves suppliers with paging
	 * 
	 * @param config
	 * @return
	 */
	public PagingLoadResult<SupplierDTO> search(FilterPagingLoadConfig config);

	/**
	 * Retrieves supplier by id
	 * 
	 * @param id
	 * @return
	 */
	public SupplierDTO find(Long id);

	/**
	 * Adds supplier in database
	 * 
	 * @param supplier
	 * @return
	 * @throws BusinessException
	 */
	public SupplierDTO create(SupplierDTO supplier) throws BusinessException;

	/**
	 * UpdatedSupplier
	 * 
	 * @param updatedSupplier
	 * @return
	 * @throws BusinessException
	 */
	public SupplierDTO update(SupplierDTO updatedSupplier) throws BusinessException;
	
	/**
	 * Deletes supplier
	 * 
	 * @param supplier
	 * @throws BusinessException
	 */
	public void delete(SupplierDTO supplier) throws BusinessException;
	
	/**
	 * Deletes list of suppliers from database
	 * 
	 * @param suppliers
	 * @throws BusinessException
	 */
	public void delete(List<SupplierDTO> suppliers) throws BusinessException;
}
