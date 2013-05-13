package com.pfe;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.pfe.server.model.HibernateUtil;
import com.pfe.server.model.Location;
import com.pfe.server.model.LocationType;
import com.pfe.server.model.Product;

public final class LocationTest {
	
//	@Test
//	public void createLocationTest() {
//		Location location = new Location();
//		location.setName("Location 2");
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		Transaction transaction = session.beginTransaction();
//		LocationType type = (LocationType) session.createCriteria(LocationType.class).add(
//				Restrictions.eq("description","store")).uniqueResult();
//		location.setType(type);
//		session.save(location);
//		transaction.commit();
//
//		Location retrieved = (Location) session.createCriteria(Location.class).add(
//				Restrictions.eq("name","Location 2")).uniqueResult();
//		assertTrue(retrieved != null);
//		session.close();
//	}

	@Test
	public void retrieveLocationTest() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Location.class);
		Criterion criterion = Restrictions.eq("name", "Location 1");
		criteria.add(criterion);
		
		Location retrieved = (Location) criteria.uniqueResult();
		List<Product> products  = retrieved.getProducts();
		for(Product p:products){
			System.out.println(p.getCreated().toString());
		}
		
		assertTrue(retrieved != null);
		session.close();
	}
}
