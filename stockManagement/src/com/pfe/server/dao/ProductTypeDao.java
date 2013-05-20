package com.pfe.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.pfe.shared.model.ProductType;



public class ProductTypeDao {

	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * Get List of product types from database
	 * @return list of all product types
	 */
	@SuppressWarnings("unchecked")
	public List<ProductType> getProductTypes() {

		DetachedCriteria criteria = DetachedCriteria.forClass(ProductType.class);

		return hibernateTemplate.findByCriteria(criteria);
	}


}
