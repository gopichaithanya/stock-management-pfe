package com.pfe.server.dao.shipment;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.shared.model.ProductType;
import com.pfe.shared.model.Shipment;

public interface ShipmentDao extends IBaseDao<Long, Shipment> {

	/**
	 * Deletes shipments
	 * 
	 * @param shipments
	 */
	public void deleteList(List<Shipment> shipments);
	
	/**
	 * Return a list of shipments with given type, current quantity greater than
	 * 0 and applying limit of results. Shipments must be ordered by creation date
	 * in ascending order.
	 * 
	 * @param start
	 * @param limit
	 * @param type
	 * @return list of found shipments
	 */
	public List<Shipment> search(int start, int limit, ProductType type);
}
