package com.pfe.server.service;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfe.client.service.ShipmentService;
import com.pfe.server.dao.shipment.ShipmentDao;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.ShipmentDTO;
import com.pfe.shared.model.Shipment;

@Service("shipmentService") 
public class ShipmentServiceImpl implements ShipmentService {
	
	@Autowired
	private ShipmentDao dao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	public void delete(ShipmentDTO shipment) throws BusinessException {
		
		//TODO manage exceptions : if not enough quantity in warehouse stock => cannot delete
		
		Long id = shipment.getId();
		Shipment entity = dozerMapper.map(shipment, Shipment.class);
		//dao.delete(entity);

	}

	@Override
	public void deleteList(List<ShipmentDTO> shipments) throws BusinessException {
		
		//TODO manage exceptions
		System.out.println("server side");
		
	}

}
