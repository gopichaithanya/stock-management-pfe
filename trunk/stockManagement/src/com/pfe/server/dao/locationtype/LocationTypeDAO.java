package com.pfe.server.dao.locationtype;

import com.pfe.server.dao.IBaseDao;
import com.pfe.server.model.LocationType;

public interface LocationTypeDAO extends IBaseDao<Long, LocationType> {

	/**
	 * Retrieves warehouse type
	 * 
	 * @return warehouse location type
	 */
	public LocationType getWarehouseType();
}
