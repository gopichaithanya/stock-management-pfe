package com.pfe;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.server.model.Location;
import com.pfe.server.model.LocationType;
import com.pfe.server.model.ProductType;
import com.pfe.server.model.Shipment;
import com.pfe.server.model.Stock;

@SuppressWarnings("unchecked")
public class LocationTest {

	@Test
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createLocationTest() {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"db-config.xml");

		SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
		HibernateTemplate ht = new HibernateTemplate(sf);

		List<LocationType> list = ht
				.find("from LocationType lt where lt.description = 'warehouse'");
		LocationType locationType = list.get(0);

		Location w = new Location();
		w.setName("Main warehouse");
		w.setType(locationType);
		ht.saveOrUpdate(w);

	}

	/**
	 * Move products
	 * 
	 */
	@Test
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void moveProductsTest() {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"db-config.xml");
		SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
		HibernateTemplate ht = new HibernateTemplate(sf);

		// we move products from the warehouse to location 1
		// Get the warehouse
		List<Location> warehouseList = ht
				.find("from Location l where l.name = 'Main warehouse'");
		Location warehouse = warehouseList.get(0);

		// Get the location 1
		List<Location> storeList = ht
				.find("from Location l where l.name = 'Store 1'");
		Location store = storeList.get(0);

		// get productType to move
		List<ProductType> types = ht
				.find("from ProductType pt where pt.name = 'pen'");
		ProductType type = types.get(0);
		// quantity to move
		int quantityToMove = 500;

		Stock warehouseStock = warehouse.getStockByType(type);
		if (warehouseStock == null) {
			// throw exception; not enough goods
		} else {
			int availableQty = warehouseStock.getQuantity();
			if (quantityToMove > availableQty) {
				// throw exception; not enough goods
			} else {
				// update warehouse quantity
				warehouse.removeProducts(type, quantityToMove);

				// update store quantity
				store.removeProducts(type, quantityToMove);

				ht.update(warehouse);
				ht.update(store);
			}
		}
	}

	/**
	 * Sell products
	 * 
	 */
	@Test
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sellProductsTest() {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"db-config.xml");
		SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
		HibernateTemplate ht = new HibernateTemplate(sf);

		// sell some products from the warehouse
		// Get the warehouse
		List<Location> warehouseList = ht
				.find("from Location l where l.name = 'Main warehouse'");
		Location warehouse = warehouseList.get(0);

		// get productType to sell
		List<ProductType> types = ht
				.find("from ProductType pt where pt.name = 'pen'");
		ProductType type = types.get(0);
		// quantity to sell
		int quantityToSell = 50;

		Stock warehouseStock = warehouse.getStockByType(type);
		if (warehouseStock == null) {
			// throw exception; not enough goods
		} else {
			int availableQty = warehouseStock.getQuantity();
			if (quantityToSell > availableQty) {
				// throw exception; not enough goods
			} else {
				// update warehouse quantity
				warehouse.removeProducts(type, quantityToSell);

				// retrieve shipments corresponding to ProductType
				String query = "from Shipment s where s.productType = ? order by s.created";
				List<Shipment> shipments = ht.find(query, type);
				
				Boolean stop = false;
				int currentQuantity = 0;
				int i = 0;
				// while quantity not sufficient, update next shipment
				while (!stop) {
					Shipment s = shipments.get(i);
					currentQuantity += s.getCurrentQuantity();
					if (currentQuantity <= quantityToSell) {
						s.setCurrentQuantity(0);
					} else {
						int newQty = currentQuantity - quantityToSell;
						s.setCurrentQuantity(newQty);
						stop = true;
					}
					ht.update(s);
					i++;
				}
				ht.update(warehouse);
			}
		}
	}
}
