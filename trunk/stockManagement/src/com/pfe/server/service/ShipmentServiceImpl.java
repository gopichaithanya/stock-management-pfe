package com.pfe.server.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.ShipmentService;
import com.pfe.server.dao.location.LocationDAO;
import com.pfe.server.dao.locationtype.LocationTypeDAO;
import com.pfe.server.dao.shipment.ShipmentDao;
import com.pfe.server.dao.stock.StockDAO;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.ShipmentDTO;
import com.pfe.shared.model.Location;
import com.pfe.shared.model.LocationType;
import com.pfe.shared.model.ProductType;
import com.pfe.shared.model.Shipment;
import com.pfe.shared.model.Stock;

@Service("shipmentService")
public class ShipmentServiceImpl implements ShipmentService {

	@Autowired
	private ShipmentDao shipmentDao;
	@Autowired
	private StockDAO stockDao;
	@Autowired
	private LocationDAO locationDao;
	@Autowired
	private LocationTypeDAO locationTypeDao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteList(List<ShipmentDTO> shipments) throws BusinessException {

		List<Shipment> entities = new ArrayList<Shipment>();
		for (ShipmentDTO shipment : shipments) {
			entities.add(dozerMapper.map(shipment, Shipment.class));
		}

		//Manage exceptions
		for (Shipment entity : entities) {

			//If shipment is in the database
			if (entity.getId() != null) {
				
				//Part of the shipment was sold
				if (entity.getCurrentQuantity() != entity.getInitialQuantity()) {
					throw new BusinessException(
							"Some of the shipments are impossible to delete because"
									+ " items have been sold.");
				}

				//Not enough goods in warehouse
				LocationType warehouseType = locationTypeDao.getWarehouseType();
				Location warehouse = locationDao.get(warehouseType).get(0);
				ProductType type = entity.getProductType();
				Stock stock = stockDao.get(warehouse, type);
				if (stock == null) {
					throw new BusinessException(
							"Some of the shipments are impossible to delete because"
									+ " there are not enought goods in the warehouse");
				} else {
					int availableQty = stock.getQuantity();
					int shipmentQty = entity.getInitialQuantity();
					if (availableQty < shipmentQty) {
						throw new BusinessException(
								"Some of the shipments are impossible to delete because"
										+ " there are not enought goods in the warehouse");
					} else {
						
						//Delete shipment and update warehouse stocks
						//TODO if stock in warehouse has qty = 0; delete it
						//TODO update debt in invoice
						int remainingQty = availableQty - shipmentQty;
						stock.setQuantity(remainingQty);
						stockDao.merge(stock);
						shipmentDao.delete(entity);
					}
				}
			}
		}
	}
}
