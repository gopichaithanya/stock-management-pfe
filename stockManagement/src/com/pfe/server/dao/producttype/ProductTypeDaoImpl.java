package com.pfe.server.dao.producttype;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.shared.model.ProductType;

@Repository
// declares dao bean
public class ProductTypeDaoImpl extends BaseDaoImpl<Long, ProductType>
		implements ProductTypeDao {

	@Autowired
	public ProductTypeDaoImpl(SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	@Override
	public ProductType getProductTypeByName(String name) {

		Criterion criterion = Restrictions.eq("name", name).ignoreCase();
		List<ProductType> l = findByCriteria(criterion);
		if (l.size() > 0) {
			return l.get(0);
		}
		return null;

	}

	@Override
	public ProductType getDuplicateName(Long excludedId, String name) {
		Criterion criterion1 = Restrictions.eq("name", name).ignoreCase();
		Criterion criterion2 = Restrictions.not(Restrictions.eq("id",
				excludedId));
		List<ProductType> l = findByCriteria(criterion1, criterion2);

		if (l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

}
