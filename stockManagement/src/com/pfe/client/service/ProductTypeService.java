package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.ProductTypeDTO;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * RPC Service that handles product type CRUD
 * 
 * @author Alexandra
 * 
 */
@RemoteServiceRelativePath("gxt3/productTypeService")
public interface ProductTypeService extends RemoteService {

	/**
	 * Retrieves all product types from database
	 * 
	 * @return
	 */
	public List<ProductTypeDTO> getAll();
	
	/**
	 * Gets type by id
	 * 
	 * @param id
	 * @return
	 */
	public ProductTypeDTO find(Long id);

	/**
	 * Adds new product type in database
	 * 
	 * @param productType
	 * @return 
	 * @throws BusinessException
	 */
	public ProductTypeDTO create(ProductTypeDTO productType) throws BusinessException;

	/**
	 * Updates product type in database
	 * 
	 * @param updatedType
	 * @return
	 * @throws BusinessException
	 */
	public ProductTypeDTO update(ProductTypeDTO updatedType) throws BusinessException;
	
	/**
	 * Removes type from database
	 * 
	 * @param productType
	 * @throws BusinessException
	 */
	public void delete(ProductTypeDTO productType) throws BusinessException;
	
	
	/**
	 * Retrieves paged product types from database 
	 * 
	 * @param config
	 * @return
	 */
	public PagingLoadResult<ProductTypeDTO> search(FilterPagingLoadConfig config);
	
	/**
	 * Retrieves types with paging and filter value
	 * 
	 * @param config
	 * @param name
	 * @return
	 */
	public PagingLoadResult<ProductTypeDTO> filter(FilterPagingLoadConfig config, String name);
}
