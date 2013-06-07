package com.pfe.server.dao.shipment;

import java.util.List;

import com.pfe.server.dao.BaseDaoImpl;
import com.pfe.shared.model.Shipment;

public class ShipmentDaoImpl extends BaseDaoImpl<Long, Shipment> implements ShipmentDao {

	@Override
	public void deleteList(List<Shipment> shipments) {
		
		for(Shipment shipment : shipments){
			delete(shipment);
		}
	}
	

}
