package com.pfe.server.dao.supplier;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.shared.model.Supplier;

@Repository
public class SupplierDaoImpl extends BaseDaoImpl<Long, Supplier> implements SupplierDao {

	@Autowired
	public SupplierDaoImpl(SessionFactory factory) {
		super.setSessionFactory(factory);
	}
	
	@Override
	public List<Supplier> search(int start, int limit, String name) {
		Criterion criterion = null;
		if (StringUtils.isNotBlank(name)){
			criterion = Restrictions.eq("name", name).ignoreCase();
		}
		
		List<Supplier> results = findByCriteria(start, limit, criterion);
		return results;
	}
}
