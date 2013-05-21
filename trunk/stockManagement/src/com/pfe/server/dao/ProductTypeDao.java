package com.pfe.server.dao;

import java.util.List;

import com.pfe.shared.model.ProductType;

public interface ProductTypeDao {

	/**
	 * Get List of product types from database
	 * 
	 * @return list of all product types
	 */
	public List<ProductType> getProductTypes();

}
