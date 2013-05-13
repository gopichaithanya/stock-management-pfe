package com.pfe;

import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.pfe.server.model.HibernateUtil;
import com.pfe.server.model.LocationType;

public class LocationTypeTest {

	@Test
	public void createTypeTest() {
		LocationType ltype = new LocationType();
		ltype.setDescription("store");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(ltype);
		transaction.commit();
		
		LocationType type = (LocationType) session.createCriteria(LocationType.class).add(
				Restrictions.eq("description","store")).uniqueResult();
		session.close();
		assertTrue(type != null);
	}
	
}
