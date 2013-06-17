package com.pfe.server.dao.shipment;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.server.dao.OrderAlias;
import com.pfe.server.dao.SortField;
import com.pfe.server.dao.shipment.sort.ShipmentCreatedSortField;
import com.pfe.server.dao.shipment.sort.ShipmentSortField;
import com.pfe.shared.model.ProductType;
import com.pfe.shared.model.Shipment;

@Repository
public class ShipmentDaoImpl extends BaseDaoImpl<Long, Shipment> implements ShipmentDao {

	@Autowired
	public ShipmentDaoImpl(SessionFactory factory) {
		super.setSessionFactory(factory);
	}
	
	@Override
	public void deleteList(List<Shipment> shipments) {
		
		for(Shipment shipment : shipments){
			delete(shipment);
		}
	}

	@Override
	public List<Shipment> search(int start, int limit, ProductType type) {
		Criterion criterion1 = Restrictions.eq("productType", type);
		Criterion criterion2 = Restrictions.gt("currentQuantity", 0);
		List<SortField> sorts = new ArrayList<SortField>();
		sorts.add(new ShipmentCreatedSortField());
		List<OrderAlias> orderAliases = getOrderAliases(sorts);
		List<Order> orders = getOrders(sorts);
		return findByCriteria(start, limit, orderAliases, orders, criterion1, criterion2);
		//return findByCriteria(start, limit, criterion1, criterion2);
	}
	
	@Override
	protected Class<? extends SortField> getSortType() {
		return ShipmentSortField.class;			
	}

}
