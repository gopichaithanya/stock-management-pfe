package com.pfe.client.service;

import org.springframework.stereotype.Service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.ShipmentDTO;

/**
 * RPC Service for Shipment CRUD operations
 * 
 * @author Alexandra
 *
 */
@Service("gxt3/shipmentService")
public interface ShipmentService extends RemoteService {

	/**
	 * Deletes shipment from database
	 * 
	 * @param shipment
	 * @throws BusinessException
	 */
	public void delete(ShipmentDTO shipment) throws BusinessException;
}
