package com.pfe.server.dao.location;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.shared.model.Location;
import com.pfe.shared.model.LocationType;

public interface LocationDAO extends IBaseDao<Long, Location> {
	
	/**
	 * Retrieves locations by type
	 * 
	 * @return list of locations of given type
	 */
	public List<Location> get(LocationType type);
}
