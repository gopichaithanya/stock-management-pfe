package com.pfe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.pfe.server.model.HibernateUtil;
import com.pfe.server.model.Invoice;
import com.pfe.server.model.Location;
import com.pfe.server.model.Product;
import com.pfe.server.model.ProductType;
import com.pfe.server.model.Supplier;

public class InvoiceTest {

	@Test
	public void createInvoiceTest() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Invoice i = new Invoice();
		i.setCode(1);
		i.setPaymentType("immediate");
		i.setRestToPay(new BigDecimal(0));
		
		//create new products
		List<Product> products = new ArrayList<Product>();
		Date date = Calendar.getInstance().getTime();
		Product p = new Product();
		p.setQuantity(100);
		p.setSold(false);
		p.setUnitPrice(new BigDecimal(102));
		p.setCreated(date);
		ProductType type = (ProductType) session
				.createCriteria(ProductType.class)
				.add(Restrictions.eq("name", "type 1")).uniqueResult();
		p.setProductType(type);
		
		//set warehouse location for new products
		Criteria criteria = session.createCriteria(Location.class);
		Criterion locName = Restrictions.eq("name", "Location 1");
		criteria.add(locName);
		Location location = (Location) criteria.uniqueResult();
		p.setLocation(location);
		
		//add products to invoice
		products.add(p);
		i.setProducts(products);
		
		//set invoice supplier
		criteria = session.createCriteria(Supplier.class);
		Criterion supplier = Restrictions.eq("name", "Supplier 1");
		criteria.add(supplier);
		Supplier s = (Supplier) criteria.uniqueResult();
		i.setSupplier(s);
		
		session.save(i);
		transaction.commit();
		session.close();
	}
	
//	@Test
//	public void deleteInvoice(){
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		Transaction transaction = session.beginTransaction();
//		
//		Criteria criteria = session.createCriteria(Invoice.class);
//		Criterion id = Restrictions.eq("code", 1);
//		criteria.add(id);
//		Invoice i = (Invoice) criteria.uniqueResult();
//		
//		session.delete(i);
//		transaction.commit();
//		session.close();
//	}

}
