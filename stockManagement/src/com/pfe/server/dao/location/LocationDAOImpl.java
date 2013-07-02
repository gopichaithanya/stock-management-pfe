package com.pfe.server.dao.location;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.server.model.LocationType;
import com.pfe.shared.model.Location;

@Repository
public class LocationDAOImpl extends BaseDaoImpl<Long, Location> implements
		LocationDAO {

	@Autowired
	public LocationDAOImpl(SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	@Override
	public List<Location> get(LocationType type) {
		Criterion criterion = Restrictions.eq("type", type);
		List<Location> l = findByCriteria(criterion);
		return l; 
	}

	@Override
	public List<Location> search(int start, int limit) {
		return findByCriteria(start, limit, (Criterion[])null);
		 
	}

	@Override
	public long countByCriteria(String name) {
		
		Criterion nameCriterion = null;
		if (StringUtils.isNotBlank(name)){
			nameCriterion = Restrictions.ilike("name", "%" + name + "%");
		}
		return countByCriteria(nameCriterion);
	}

	@Override
	public List<Location> search(int start, int limit, String name) {
		
		Criterion criterion = null;
		if (StringUtils.isNotBlank(name)){
			criterion = Restrictions.ilike("name", "%" + name + "%");
		}
		
		List<Location> results = findByCriteria(start, limit, criterion);
		return results;
		
	}

}
