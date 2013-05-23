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
	 * Updates product type
	 * 
	 * @param productType
	 */
	public ProductType updateProductType(ProductType productType);
	
	/**
	 * Retrieves product type by name
	 * 
	 * @param name
	 * @return
	 */
	public ProductType getPTypeByName(String name);
	
	/**
	 * Retrieves product type by id
	 * 
	 * @param excludedId
	 * @param name
	 * @return
	 */
	public ProductType getDuplicateName(Long excludedId, String name);
}
