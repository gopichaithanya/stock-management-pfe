package com.pfe;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.pfe.server.model.HibernateUtil;
import com.pfe.server.model.Location;
import com.pfe.server.model.Product;
import com.pfe.server.model.ProductType;

public class ProductTest {

	@Test
	public void createProductTest() {

		Date date = Calendar.getInstance().getTime();
		Product p = new Product();
		p.setQuantity(100);
		p.setSold(false);
		p.setUnitPrice(new BigDecimal(12));
		p.setCreated(date);

		Session session = HibernateUtil.getSessionFactory().openSession();
		Location location = (Location) session.createCriteria(Location.class)
				.add(Restrictions.eq("name", "Location 1")).uniqueResult();
		p.setLocation(location);

		ProductType type = (ProductType) session
				.createCriteria(ProductType.class)
				.add(Restrictions.eq("name", "type 1")).uniqueResult();
		p.setProductType(type);

		Transaction transaction = session.beginTransaction();
		session.save(p);
		transaction.commit();

		Product retrieved = (Product) session.createCriteria(Product.class)
				.add(Restrictions.eq("created", date)).uniqueResult();
		assertTrue(retrieved != null);

		session.close();
	}

}
