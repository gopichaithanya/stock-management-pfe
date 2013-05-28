package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
import com.pfe.shared.model.ProductType;
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
	 * @return list of all types
	 */
	public List<ProductType> getAll();

	/**
	 * Adds new product type in database
	 * 
	 * @param productType
	 * @return 
	 * @throws BusinessException
	 */
	public ProductType create(ProductType productType)
			throws BusinessException;

	/**
	 *  Updates product type in database
	 * 
	 * @param initial type 
	 * @param updatedBuffer 
	 * @return updated type retrieved from database
	 * @throws BusinessException
	 */
	public ProductType update(ProductType initial, ProductType updatedBuffer)
			throws BusinessException;
	
	/**
	 * Removes type from database
	 * 
	 * @param productType
	 * @throws BusinessException
	 */
	public void delete(ProductType productType) throws BusinessException;
	
	
	/**
	 * Retrieves paged product types from database 
	 * 
	 * @param config
	 * @return
	 */
	public PagingLoadResult<ProductType> search(FilterPagingLoadConfig config);
	
	public PagingLoadResult<ProductType> filter(FilterPagingLoadConfig config, String name);
}
