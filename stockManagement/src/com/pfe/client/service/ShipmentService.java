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
	 * Deletes list of records
	 * 
	 * @param shipments
	 * @throws BusinessException
	 */
	public void delete(List<ShipmentDTO> shipments) throws BusinessException;
}
