package com.pfe.server.dao.location;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.server.model.LocationType;
import com.pfe.shared.model.Location;

public interface LocationDAO extends IBaseDao<Long, Location> {
	
	/**
	 * Retrieves locations by type
	 * 
	 * @return list of locations of given type
	 */
	public List<Location> get(LocationType type);
	
	/**
	 * Retrieves records applying limit of results.
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Location> search(int start, int limit); 
	
	/**
	 * Retrieves records applying limit of results where name like parameter.
	 * Name is ignored if null or blank. Case is ignored for name filter.
	 * 
	 * @param start
	 * @param limit
	 * @param name
	 * @return
	 */
	public List<Location> search(int start, int limit, String name);
	
	/**
	 * Counts records when location name like given parameter. Case is ignored.
	 * 
	 * @param name
	 * @return
	 */
	public long countByCriteria(String name);
}
