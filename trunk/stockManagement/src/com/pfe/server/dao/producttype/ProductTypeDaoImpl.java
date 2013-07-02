package com.pfe.server.dao.producttype;

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
import com.pfe.server.model.ProductType;

@Repository
// declares dao bean
public class ProductTypeDaoImpl extends BaseDaoImpl<Long, ProductType>
		implements ProductTypeDao {

	@Autowired
	public ProductTypeDaoImpl(SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	@Override
	public ProductType getDuplicateName(Long excludedId, String name) {
		Criterion criterion1 = Restrictions.eq("name", name).ignoreCase();
		Criterion criterion2 = Restrictions.not(Restrictions.eq("id", excludedId));
		List<ProductType> l = findByCriteria(criterion1, criterion2);
		return l.size() > 0 ? l.get(0) : null;
	}

	@Override
	public ProductType search(String name) {
		Criterion criterion = Restrictions.eq("name", name).ignoreCase();
		List<ProductType> l = findByCriteria(criterion);
		return l.size() > 0 ? l.get(0) : null; 
	}

	@Override
	public List<ProductType> search(int start, int limit, String name) {
		
		List<SortField> sorts = new ArrayList<SortField>();
		
		//Retrieve types in alphabetical order by name
		sorts.add(new ProductTypeNameSortField(/*ascending*/true));
		List<OrderAlias> orderAliases = getOrderAliases(sorts);
		List<Order> orders = getOrders(sorts);
		
		Criterion criterion = null;
		if (StringUtils.isNotBlank(name)){
			criterion = Restrictions.ilike("name", "%" + name + "%");
		}
		
		List<ProductType> results = findByCriteria(start, limit, orderAliases, orders, criterion);
		return results;
	}

	@Override
	public long countByCriteria(String name) {
		
		Criterion criterion = null;
		if (StringUtils.isNotBlank(name)){
			criterion = Restrictions.ilike("name", "%" + name + "%");
		}
		return countByCriteria(criterion);
	}
	
	@Override
	protected Class<? extends SortField> getSortType() {
		return ProductTypeSortField.class;
	}

}
