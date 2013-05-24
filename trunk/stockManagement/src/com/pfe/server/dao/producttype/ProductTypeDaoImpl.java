package com.pfe.server.dao.producttype;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.shared.model.ProductType;

@Repository
// declares dao bean
public class ProductTypeDaoImpl extends BaseDaoImpl<Long, ProductType> implements ProductTypeDao {
	
	 @Autowired
	 public ProductTypeDaoImpl(SessionFactory factory){
		 super.setSessionFactory(factory);
	 }

	@Override
	@SuppressWarnings("unchecked")
	public ProductType getProductTypeByName(String name) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ProductType.class);
		criteria.add(Restrictions.eq("name", name).ignoreCase());
		List<ProductType> l = getHibernateTemplate().findByCriteria(criteria);
		if (l.size() > 0) {
			return l.get(0);
		}
		return null;

	}

	@Override
	@SuppressWarnings("unchecked")
	public ProductType getDuplicateName(Long excludedId, String name) {

		DetachedCriteria criteria = DetachedCriteria
				.forClass(ProductType.class);
		criteria.add(Restrictions.conjunction()
				.add(Restrictions.eq("name", name))
				.add(Restrictions.not(Restrictions.eq("id", excludedId))));

		List<ProductType> l = getHibernateTemplate().findByCriteria(criteria);
		if (l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public void deleteProductType(ProductType productType) {
		getHibernateTemplate().delete(productType);
		
	}

}
