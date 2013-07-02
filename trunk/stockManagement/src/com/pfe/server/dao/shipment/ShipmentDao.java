package com.pfe.server.dao.shipment;

import java.util.List;

import com.pfe.server.dao.IBaseDao;
import com.pfe.server.model.ProductType;
import com.pfe.shared.model.Shipment;

public interface ShipmentDao extends IBaseDao<Long, Shipment> {

	/**
	 * Deletes shipments
	 * 
	 * @param shipments
	 */
	public void deleteList(List<Shipment> shipments);
	
	/**
	 * Returns a list of shipments with given type and applying limit of results. 
	 * Shipments are ordered by creation date in ascending order. If the sold parameter
	 * is false then shipments with current quantity = 0 are not retrieved.
	 * 
	 * @param start
	 * @param limit
	 * @param type
	 * @param sold
	 * @return
	 */
	public List<Shipment> search(int start, int limit, ProductType type, Boolean sold);
}
