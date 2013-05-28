package com.pfe.server.dao.producttype;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
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
		Criterion criterion = null;
		if (StringUtils.isNotBlank(name)){
			criterion = Restrictions.eq("name", name).ignoreCase();
		}
		
		List<ProductType> results = findByCriteria(start, limit, criterion);
		return results;
	}

	@Override
	public List<ProductType> searchLike(int start, int limit, String likeName) {
		Criterion criterion = null;
		if (StringUtils.isNotBlank(likeName)){
			criterion = Restrictions.ilike("name", "%"+likeName+"%");
		}
		
		List<ProductType> results = findByCriteria(start, limit, criterion);
		return results;
	}

	@Override
	public long countLike(String likeName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductType.class);
		Criterion criterion = Restrictions.ilike("name", "%"+likeName+"%");
		criteria.add(criterion);
		
		return countByCriteria(criteria);
	}

}
