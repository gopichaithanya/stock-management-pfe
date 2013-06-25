package com.pfe.server.dao.supplier;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.server.dao.OrderAlias;
import com.pfe.server.dao.SortField;
import com.pfe.shared.model.Supplier;

@Repository
public class SupplierDaoImpl extends BaseDaoImpl<Long, Supplier> implements SupplierDao {

	@Autowired
	public SupplierDaoImpl(SessionFactory factory) {
		super.setSessionFactory(factory);
	}
	
	@Override
	public List<Supplier> search(int start, int limit, String name) {
		
		List<SortField> sorts = new ArrayList<SortField>();
		//Retrieve suppliers in alphabetical order by name
		sorts.add(new SupplierNameSortField(/*ascending*/true));
		List<OrderAlias> orderAliases = getOrderAliases(sorts);
		List<Order> orders = getOrders(sorts);
		
		Criterion criterion = null;
		if (StringUtils.isNotBlank(name)){
			criterion = Restrictions.eq("name", name).ignoreCase();
		}
		
		List<Supplier> results = findByCriteria(start, limit, orderAliases, orders, criterion);
		return results;
	}

	@Override
	public Supplier search(String name) {
		Criterion criterion = Restrictions.eq("name", name).ignoreCase();
		List<Supplier> l = findByCriteria(criterion);
		return l.size() > 0 ? l.get(0) : null; 
	}

	@Override
	public Supplier getDuplicateName(Long excludedId, String name) {
		Criterion criterion1 = Restrictions.eq("name", name).ignoreCase();
		Criterion criterion2 = Restrictions.not(Restrictions.eq("id", excludedId));
		List<Supplier> l = findByCriteria(criterion1, criterion2);
		return l.size() > 0 ? l.get(0) : null;
	}
	
	@Override
	protected Class<? extends SortField> getSortType() {
		return SupplierSortField.class;
	}
}
