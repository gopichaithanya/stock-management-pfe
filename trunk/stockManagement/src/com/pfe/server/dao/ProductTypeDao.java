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
	 * @return type with given name
	 */
	public ProductType getProductTypeByName(String name);

	/**
	 * Retrieves product type with given name where id different from excludedId
	 * 
	 * @param excludedId
	 * @param name
	 * @return type with given name and id different from excludedId
	 */
	public ProductType getDuplicateName(Long excludedId, String name);
	
	/**
	 * Deletes type from database
	 * 
	 * @param productType
	 */
	public void deleteProductType(ProductType productType);
}
