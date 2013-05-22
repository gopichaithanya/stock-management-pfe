package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
	 */
	public ProductType createProductType(ProductType productType);
}
