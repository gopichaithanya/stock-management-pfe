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
	 * Retrieves shipments from start index where product type like parameter.
	 * The number if records retrieved is equal to limit parameter. Type is
	 * ignored if null.
	 * 
	 * @param start
	 * @param limit
	 * @param type
	 * @return list of found shipments
	 */
	public List<Shipment> search(int start, int limit, ProductType type);
}
