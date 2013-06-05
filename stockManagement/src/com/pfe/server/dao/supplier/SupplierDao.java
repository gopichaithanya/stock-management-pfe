package com.pfe.server.dao.supplier;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.shared.model.Supplier;

public interface SupplierDao extends IBaseDao<Long, Supplier> {
	
	/**
	 * Retrieves supplier by name
	 * 
	 * @param name
	 * @return
	 */
	public Supplier search(String name);
	
	/**
	 * Retrieves records from start to limit index where name like parameter.
	 * Name is ignored if null or blank
	 * 
	 * @param start
	 * @param limit
	 * @param name
	 * @return
	 */
	public List<Supplier> search(int start, int limit, String name); 

}
