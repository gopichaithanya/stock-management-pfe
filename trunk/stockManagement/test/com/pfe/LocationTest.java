package com.pfe;

import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.pfe.server.model.HibernateUtil;
import com.pfe.server.model.Location;
import com.pfe.server.model.LocationType;

public final class LocationTest {
	
	@Test
	public void createLocationTest() {
		Location location = new Location();
		location.setName("Location test");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		LocationType type = (LocationType) session.createCriteria(LocationType.class).add(
				Restrictions.eq("description","warehouse")).uniqueResult();
		location.setType(type);
		session.save(location);
		transaction.commit();

		LocationType retrieved = (LocationType) session.createCriteria(LocationType.class).add(
				Restrictions.eq("name","Location test")).uniqueResult();
		assertTrue(retrieved != null);
		session.close();
	}
}
