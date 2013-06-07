package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.ShipmentDTO;

/**
 * RPC Service for Shipment CRUD operations
 * 
 * @author Alexandra
 *
 */
@RemoteServiceRelativePath("gxt3/shipmentService")
public interface ShipmentService extends RemoteService {

	/**
	 * Deletes shipment from database
	 * 
	 * @param shipment
	 * @throws BusinessException
	 */
	public void delete(ShipmentDTO shipment) throws BusinessException;
	
	/**
	 * Deletes shipments
	 * 
	 * @param shipments
	 * @throws BusinessException
	 */
	public void deleteList(List<ShipmentDTO> shipments) throws BusinessException;
}
