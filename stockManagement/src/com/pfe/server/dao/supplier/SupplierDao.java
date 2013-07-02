package com.pfe.server.dao.supplier;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.server.model.Supplier;

public interface SupplierDao extends IBaseDao<Long, Supplier> {

	/**
	 * Retrieves supplier by name. The match needs to be exact, but case is
	 * ignored.
	 * 
	 * @param name
	 * @return
	 */
	public Supplier search(String name);

	/**
	 * Retrieves supplier with the exact given name but id different from
	 * excludedId
	 * 
	 * @param excludedId
	 * @param name
	 * @return
	 */
	public Supplier getDuplicateName(Long excludedId, String name);

	/**
	 * Retrieves records applying limit of results where name like parameter.
	 * Name is ignored if null or blank. Records are retrieved in alphabetical
	 * order by name. Case is ignored for name filter.
	 * 
	 * @param start
	 * @param limit
	 * @param name
	 * @return
	 */
	public List<Supplier> search(int start, int limit, String name);

	/**
	 * Counts records where name like given parameter. Case is ignored.
	 * 
	 * @param name
	 * @return
	 */
	public long countByCriteria(String name);

}
