package com.pfe.server.dao.shipment;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pfe.server.dao.BaseDaoImpl;
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
		Criterion criterion = Restrictions.eq("productType", type);
		return findByCriteria(start, limit, criterion);
	}
	

}
