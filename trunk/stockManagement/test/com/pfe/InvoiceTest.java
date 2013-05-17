package com.pfe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.server.model.Invoice;
import com.pfe.server.model.Location;
import com.pfe.server.model.ProductType;
import com.pfe.server.model.Shipment;
import com.pfe.server.model.Stock;
import com.pfe.server.model.Supplier;

@SuppressWarnings("unchecked")
public class InvoiceTest {

	@Test
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void receiveInvoiceTest() {

		//Database connection
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"db-config.xml");
		SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
		HibernateTemplate ht = new HibernateTemplate(sf);
		
		//Invoice attributes 
		Invoice i = new Invoice();
		Date date = Calendar.getInstance().getTime();
		i.setCode(20);
		i.setCreated(date);
		i.setPaymentType("onSale");
		i.setRestToPay(new BigDecimal(0));
		
		//Shipments
		List<Shipment> shipments = new ArrayList<Shipment>();
		int qty = 300;
		Shipment s1 = new Shipment();
		s1.setCreated(date);
		s1.setCurrentQuantity(qty);
		s1.setInitialQuantity(qty);
		s1.setPaid(false);
		s1.setUnitPrice(new BigDecimal(20));
		List<ProductType> types = ht
				.find("from ProductType pt where pt.name = 'pen'");
		ProductType type = types.get(0);
		s1.setProductType(type);
		s1.setInvoice(i);
		shipments.add(s1);
		i.setShipments(shipments);
		
		//Supplier
		List<Supplier> list = ht
				.find("from Supplier s where s.name = 'Supplier 1'");
		Supplier s = list.get(0);
		i.setSupplier(s);
		
		//Update warehouse stocks
		List<Location> locations = ht
				.find("from Location l where l.name = 'Main warehouse'");
		Location warehouse = locations.get(0);
		Stock stock = warehouse.getStockByType(type);
		if(stock == null){
			stock = new Stock();
			stock.setType(type);
			stock.setQuantity(qty);
			warehouse.getStocks().add(stock);
		} else{
			int newQty = stock.getQuantity() + qty;
			stock.setQuantity(newQty);
		}
		
		ht.saveOrUpdate(warehouse);
		ht.saveOrUpdate(i);
	}
	
	@Test
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteInvoiceTest() {

		//Database connection
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"db-config.xml");
		SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
		HibernateTemplate ht = new HibernateTemplate(sf);
		
		//Get the warehouse
		List<Location> locations = ht
				.find("from Location l where l.name = 'Main warehouse'");
		Location warehouse = locations.get(0);
		
		//Get invoice to delete
		List<Invoice> list = ht.find("from Invoice i where i.code = 20");
		Invoice i = list.get(0);
		
		List<Shipment> shipments = i.getShipments();
		for(Shipment s : shipments){
			ProductType pType = s.getProductType();
			int qty = s.getCurrentQuantity();
			Stock warehouseStock = warehouse.getStockByType(pType);
			if(warehouseStock == null){
				//can't delete ---> rollback everything
			} else{
				int warehouseQty = warehouseStock.getQuantity();
				if(warehouseQty < qty){
					//can't delete
				} else{
					int newQty = warehouseStock.getQuantity() - qty;
					warehouseStock.setQuantity(newQty);
				}
			}
		}
		ht.delete(i);
		ht.update(warehouse);
	}
}
