package com.pfe;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.pfe.server.model.HibernateUtil;
import com.pfe.server.model.Supplier;

public class SupplierTest {

	@Test
	public void createSupplierTest(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Supplier s = new Supplier();
		s.setName("Supplier 1");
		s.setDescription("Supplier 1 desc");
		session.save(s);
		transaction.commit();
		session.close();
	}

}
