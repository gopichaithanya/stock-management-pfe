package com.pfe.server.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.pfe.shared.model.ProductType;

@Repository //declares dao bean
public class ProductTypeDaoImpl implements ProductTypeDao{

	private HibernateTemplate hibernateTemplate;

	@Autowired // session factory injection
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductType> getProductTypes() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductType.class);

		return hibernateTemplate.findByCriteria(criteria);
	}


}
