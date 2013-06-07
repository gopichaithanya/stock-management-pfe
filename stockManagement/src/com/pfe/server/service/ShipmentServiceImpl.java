package com.pfe.server.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfe.client.service.ShipmentService;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.ShipmentDTO;

@Service("shipmentService") 
public class ShipmentServiceImpl implements ShipmentService {
	
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	public void delete(ShipmentDTO shipment) throws BusinessException {
		// TODO Auto-generated method stub

	}

}
