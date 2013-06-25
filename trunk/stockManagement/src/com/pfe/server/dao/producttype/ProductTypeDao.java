package com.pfe.server.dao.producttype;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.shared.model.ProductType;

public interface ProductTypeDao extends IBaseDao<Long, ProductType> {

	/**
	 * Retrieves product type by name. Name match must be exact, but name case
	 * is ignored.
	 * 
	 * @param name
	 * @return type with given name
	 */
	public ProductType search(String name);

	/**
	 * Retrieves product type with given name where id different from
	 * excludedId. Name match must be exact.
	 * 
	 * @param excludedId
	 * @param name
	 * @return type with given name and id different from excludedId
	 */
	public ProductType getDuplicateName(Long excludedId, String name);

	/**
	 * Retrieves records from start to limit index where name like given
	 * parameter. Name case is ignored and records are retrieved in alphabetical
	 * order by name.s
	 * 
	 * @param start
	 * @param limit
	 * @param name
	 * @return
	 */
	public List<ProductType> search(int start, int limit, String name);

	/**
	 * Counts records with name like given parameter.
	 * 
	 * @param name
	 * @return
	 */
	public long countByCriteria(String name);

}
