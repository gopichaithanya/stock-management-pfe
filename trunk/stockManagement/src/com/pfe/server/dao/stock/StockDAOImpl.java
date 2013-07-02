package com.pfe.server.dao.stock;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.server.model.Location;
import com.pfe.server.model.ProductType;
import com.pfe.server.model.Stock;

@Repository
public class StockDAOImpl extends BaseDaoImpl<Long, Stock> implements StockDAO {

	@Autowired
	public StockDAOImpl(SessionFactory factory) {
		super.setSessionFactory(factory);
	}

	@Override
	public Stock get(Location location, ProductType type) {
		
		Criterion criterion1 = Restrictions.eq("location", location);
		Criterion criterion2 = Restrictions.eq("type", type);
		List<Stock> l = findByCriteria(criterion1, criterion2);
		return l.size() > 0 ? l.get(0) : null; 
	}

	@Override
	public List<Stock> get(ProductType type) {
		Criterion criterion = Restrictions.eq("type", type);
		return findByCriteria(criterion);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Stock> search(String typeName) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Stock.class).createCriteria("type");
		Criterion criterion = Restrictions.ilike("name", "%" + typeName + "%");
		detachedCriteria.add(criterion);
		return (List<Stock>)getHibernateTemplate().findByCriteria(detachedCriteria);
	}

}
