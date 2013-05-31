package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.SupplierDto;
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
	public List<SupplierDto> getAll();

	/**
	 * Retrieves suppliers with paging
	 * 
	 * @param config
	 * @return
	 */
	public PagingLoadResult<SupplierDto> search(FilterPagingLoadConfig config);

	/**
	 * Retrieves supplier by id
	 * 
	 * @param id
	 * @return
	 */
	public SupplierDto find(Long id);

	/**
	 * Adds supplier in database
	 * 
	 * @param supplier
	 * @return
	 * @throws BusinessException
	 */
	public SupplierDto create(SupplierDto supplier) throws BusinessException;

	/**
	 * Updates supplier
	 * 
	 * @param supplier
	 * @return
	 * @throws BusinessException
	 */
	public SupplierDto update(SupplierDto initial, SupplierDto buffer) throws BusinessException;
}
