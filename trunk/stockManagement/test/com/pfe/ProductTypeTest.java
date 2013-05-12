package com.pfe;

import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.pfe.server.model.HibernateUtil;
import com.pfe.server.model.ProductType;

public class ProductTypeTest {

	@Test
	public void createTypeTest() {
		ProductType type = new ProductType();
		type.setName("test type");
		type.setDescription("description");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(type);
		transaction.commit();

		ProductType retrieved = (ProductType) session.createCriteria(ProductType.class).add(
				Restrictions.eq("name","test type")).uniqueResult();
		assertTrue(retrieved != null);
		
		transaction = session.beginTransaction();
		session.delete(retrieved);
		transaction.commit();
		session.close();
	}
	
}
