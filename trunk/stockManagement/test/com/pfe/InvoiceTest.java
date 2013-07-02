package com.pfe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.server.model.Location;
import com.pfe.server.model.ProductType;
import com.pfe.server.model.Shipment;
import com.pfe.server.model.Stock;
import com.pfe.server.model.Supplier;
import com.pfe.shared.model.Invoice;

@SuppressWarnings("unchecked")
public class InvoiceTest {

	/**
	 * Receive new invoice 
	 * 
	 */
	@Test
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void receiveInvoiceTest() {

		// Database connection
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"db-config.xml");
		SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
		HibernateTemplate ht = new HibernateTemplate(sf);

		// Invoice attributes
		Invoice i = new Invoice();
		Date date = Calendar.getInstance().getTime();
		String paymentType = "onSale";
		i.setCode(202);
		i.setCreated(date);
		i.setPaymentType(paymentType);
		i.setRestToPay(new BigDecimal(0));

		// Shipments
		List<Shipment> shipments = new ArrayList<Shipment>();
		int qty = 1000;
		Shipment s1 = new Shipment();
		s1.setCreated(date);
		s1.setCurrentQuantity(qty);
		s1.setInitialQuantity(qty);
		if (paymentType.equals("onSale")) {
			s1.setPaid(false);
		} else {
			s1.setPaid(true);
		}

		s1.setUnitPrice(new BigDecimal(20));
		//set shipment type
		DetachedCriteria pTypeCriteria = DetachedCriteria.forClass(ProductType.class);
		pTypeCriteria.add(Restrictions.eq("name", "pen"));
		List<ProductType> types = ht.findByCriteria(pTypeCriteria);
		ProductType type = types.get(0);
	
		s1.setProductType(type);
		s1.setInvoice(i);
		shipments.add(s1);
		i.setShipments(shipments);

		// Supplier
		DetachedCriteria supplierCriteria = DetachedCriteria.forClass(Supplier.class);
		supplierCriteria.add(Restrictions.eq("name", "Supplier 2"));
		List<Supplier> suppliers = ht.findByCriteria(supplierCriteria);
		Supplier supplier = suppliers.get(0);
		i.setSupplier(supplier);

		// Update warehouse stocks
		//get the warehouse
		DetachedCriteria locationCriteria = DetachedCriteria.forClass(Location.class);
		locationCriteria.add(Restrictions.eq("name", "Main warehouse"));
		List<Location> warehouses = ht.findByCriteria(locationCriteria);
		Location warehouse = warehouses.get(0);
		
		//get corresponding stocks
		Stock stock;
		DetachedCriteria stockCriteria = DetachedCriteria.forClass(Stock.class);
		Criterion criterion1 = Restrictions.eq("location", warehouse);
		Criterion criterion2 = Restrictions.eq("type", type);
		stockCriteria.add(criterion1); stockCriteria.add(criterion2);
		List<Stock> list = ht.findByCriteria(stockCriteria);
		//no stocks of this type so create one
		if(list.size() < 1){
			stock = new Stock();
			stock.setType(type);
			stock.setQuantity(qty);
			stock.setLocation(warehouse);
		} else {
			stock = list.get(0);
			int newQty = stock.getQuantity() + qty;
			stock.setQuantity(newQty);
			stock.setLocation(warehouse);
		}

		ht.saveOrUpdate(stock);
		ht.saveOrUpdate(i);
	}

	/**
	 * Delete invoice
	 * 
	 */
	@Test
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteInvoiceTest() {

		// Database connection
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"db-config.xml");
		SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
		HibernateTemplate ht = new HibernateTemplate(sf);

		// Get the warehouse
		DetachedCriteria locationCriteria = DetachedCriteria.forClass(Location.class);
		locationCriteria.add(Restrictions.eq("name", "Main warehouse"));
		List<Location> warehouses = ht.findByCriteria(locationCriteria);
		Location warehouse = warehouses.get(0);

		// Get invoice to delete
		DetachedCriteria invoiceCriteria = DetachedCriteria.forClass(Invoice.class);
		invoiceCriteria.add(Restrictions.eq("code", 201));
		List<Invoice> invoices = ht.findByCriteria(invoiceCriteria);
		
		if (invoices.size() > 0) {
			Invoice i = invoices.get(0);

			List<Shipment> shipments = i.getShipments();
			// for each shipment to delete, remove products from
			// warehouse stocks if possible
			for (Shipment s : shipments) {
				ProductType pType = s.getProductType();
				int qty = s.getCurrentQuantity();
				
				DetachedCriteria stockCriteria = DetachedCriteria.forClass(Stock.class);
				Criterion criterion1 = Restrictions.eq("location", warehouse);
				Criterion criterion2 = Restrictions.eq("type", pType);
				stockCriteria.add(criterion1); stockCriteria.add(criterion2);
				List<Stock> list = ht.findByCriteria(stockCriteria);

				if (list.size() == 0) {
					// can't delete ---> throw exception
				} else {
					Stock warehouseStock = list.get(0);
					int warehouseQty = warehouseStock.getQuantity();
					if (warehouseQty < qty) {
						// can't delete, throw exception
					} else {
						int newQty = warehouseStock.getQuantity() - qty;
						warehouseStock.setQuantity(newQty);
						ht.merge(warehouseStock);
					}
				}
			}
			ht.delete(i);
		}
	}
}
