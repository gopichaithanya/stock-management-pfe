package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.model.BusinessException;
import com.pfe.shared.model.ProductType;

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
	 * Updates product type in database
	 * 
	 * @param productType
	 * @throws BusinessException
	 */
	public ProductType updateProductType(ProductType initial, ProductType updatedBuffer)
			throws BusinessException;
}
