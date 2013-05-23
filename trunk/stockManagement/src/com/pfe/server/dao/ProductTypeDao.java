package com.pfe.server.dao;

import java.util.List;

import com.pfe.shared.model.ProductType;

public interface ProductTypeDao {

	/**
	 * Retrieves all product types from database
	 * 
	 * @return list of all product types
	 */
	public List<ProductType> getProductTypes();
	
	/**
	 * Adds new product type in database
	 * 
	 * @param productType
	 */
	public ProductType createProductType(ProductType productType);


	/**
	 * 
	 * @param productType
	 */
	public void updateProductType(ProductType productType);
}