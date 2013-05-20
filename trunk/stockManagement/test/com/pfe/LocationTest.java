package com.pfe;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.shared.model.Location;
import com.pfe.shared.model.LocationType;
import com.pfe.shared.model.ProductType;
import com.pfe.shared.model.Shipment;
import com.pfe.shared.model.Stock;

@SuppressWarnings("unchecked")
public class LocationTest {

	@Test
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createLocationTest() {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"db-config.xml");
		SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
		HibernateTemplate ht = new HibernateTemplate(sf);
		
		DetachedCriteria locationTypeCriteria = DetachedCriteria.forClass(LocationType.class);
		locationTypeCriteria.add(Restrictions.eq("description", "warehouse"));
		List<LocationType> types = ht.findByCriteria(locationTypeCriteria);
		LocationType warehouseType = types.get(0);

		Location w = new Location();
		w.setName("Main warehouse");
		w.setType(warehouseType);
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

		// we move products from the warehouse to store 1
		// Get the warehouse	
		DetachedCriteria locationCriteria = DetachedCriteria.forClass(Location.class);
		locationCriteria.add(Restrictions.eq("name", "Main warehouse"));
		List<Location> warehouses = ht.findByCriteria(locationCriteria);
		Location warehouse = warehouses.get(0);

		// Get location 1
		locationCriteria = DetachedCriteria.forClass(Location.class);
		locationCriteria.add(Restrictions.eq("name", "Store 1"));
		List<Location> stores = ht.findByCriteria(locationCriteria);
		Location store = stores.get(0);

		// get productType to move
		DetachedCriteria pTypeCriteria = DetachedCriteria.forClass(ProductType.class);
		pTypeCriteria.add(Restrictions.eq("name", "pen"));
		List<ProductType> types = ht.findByCriteria(pTypeCriteria);
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
				store.receiveProducts(type, quantityToMove);

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
		DetachedCriteria locationCriteria = DetachedCriteria.forClass(Location.class);
		locationCriteria.add(Restrictions.eq("name", "Main warehouse"));
		List<Location> warehouses = ht.findByCriteria(locationCriteria);
		Location warehouse = warehouses.get(0);

		// get productType to sell
		DetachedCriteria criteriaProductType = DetachedCriteria.forClass(ProductType.class);
		criteriaProductType.add(Restrictions.eq("name", "pen"));
		List<ProductType> types = ht.findByCriteria(criteriaProductType);
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
				DetachedCriteria criteriaShipments = DetachedCriteria.forClass(Shipment.class);
				criteriaShipments.add(Restrictions.eq("productType", type));
				criteriaShipments.addOrder(Order.asc("created"));
				List<Shipment> shipments = ht.findByCriteria(criteriaShipments);
				
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
