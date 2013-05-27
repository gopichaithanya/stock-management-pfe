package com.pfe.server.dao.producttype;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.shared.model.ProductType;

public interface ProductTypeDao extends IBaseDao<Long, ProductType>{


	/**
	 * Retrieves product type by name
	 * 
	 * @param name
	 * @return type with given name
	 */
	public ProductType search(String name);

	/**
	 * Retrieves product type with given name where id different from excludedId
	 * 
	 * @param excludedId
	 * @param name
	 * @return type with given name and id different from excludedId
	 */
	public ProductType getDuplicateName(Long excludedId, String name);
	
	/**
	 * 
	 * @param start
	 * @param limit
	 * @param name
	 * @return
	 */
	public List<ProductType> search(int start, int limit, String name); 
	
}
