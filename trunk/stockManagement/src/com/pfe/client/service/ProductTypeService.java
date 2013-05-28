package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
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
	public List<ProductType> getProductTypes();

	/**
	 * Adds new product type in database
	 * 
	 * @param productType
	 * @return 
	 * @throws BusinessException
	 */
	public ProductType createProductType(ProductType productType)
			throws BusinessException;

	/**
	 *  Updates product type in database
	 * 
	 * @param initial type 
	 * @param updatedBuffer 
	 * @return updated type retrieved from database
	 * @throws BusinessException
	 */
	public ProductType updateProductType(ProductType initial, ProductType updatedBuffer)
			throws BusinessException;
	
	/**
	 * Removes type from database
	 * 
	 * @param productType
	 * @throws BusinessException
	 */
	public void deleteProductType(ProductType productType) throws BusinessException;
	
	
	/**
	 * Retrieves product types from database 
	 * 
	 * @param config
	 * @return
	 */
	public PagingLoadResult<ProductType> search(PagingLoadConfig config);
}
