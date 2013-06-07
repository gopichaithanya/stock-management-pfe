package com.pfe.server.dao.shipment;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.shared.model.Shipment;

public interface ShipmentDao extends IBaseDao<Long, Shipment> {

	/**
	 * Deletes shipmenst
	 * 
	 * @param shipments
	 */
	public void deleteList(List<Shipment> shipments);
}
