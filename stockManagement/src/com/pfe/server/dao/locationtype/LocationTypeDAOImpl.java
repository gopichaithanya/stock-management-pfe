package com.pfe.server.dao.locationtype;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.server.model.LocationType;

@Repository
public class LocationTypeDAOImpl extends BaseDaoImpl<Long, LocationType>
		implements LocationTypeDAO {

	@Autowired
	public LocationTypeDAOImpl(SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	@Override
	public LocationType getWarehouseType() {
		Criterion criterion = Restrictions.eq("description", "warehouse").ignoreCase();
		List<LocationType> l = findByCriteria(criterion);
		return l.size() > 0 ? l.get(0) : null; 
	}

}
