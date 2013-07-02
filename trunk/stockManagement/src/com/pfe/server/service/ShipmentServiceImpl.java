package com.pfe.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.ShipmentService;
import com.pfe.server.dao.invoice.InvoiceDao;
import com.pfe.server.dao.location.LocationDAO;
import com.pfe.server.dao.locationtype.LocationTypeDAO;
import com.pfe.server.dao.shipment.ShipmentDao;
import com.pfe.server.dao.stock.StockDAO;
import com.pfe.server.model.Location;
import com.pfe.server.model.LocationType;
import com.pfe.server.model.ProductType;
import com.pfe.server.model.Stock;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.ShipmentDTO;
import com.pfe.shared.model.Invoice;
import com.pfe.shared.model.Shipment;

@Service("shipmentService")
public class ShipmentServiceImpl implements ShipmentService {

	@Autowired
	private ShipmentDao shipmentDao;
	@Autowired
	private StockDAO stockDao;
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private LocationDAO locationDao;
	@Autowired
	private LocationTypeDAO locationTypeDao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(List<ShipmentDTO> shipments) throws BusinessException {

		List<Shipment> entities = new ArrayList<Shipment>();
		for (ShipmentDTO shipment : shipments) {
			entities.add(shipmentDao.get(shipment.getId()));
		}

		//Manage exceptions
		for (Shipment entity : entities) {

			//If shipment is in the database
			if (entity.getId() != null) {
				
				//Part of the shipment was sold
				if (entity.getCurrentQuantity() != entity.getInitialQuantity()) {
					throw new BusinessException(
						"Some shipments are impossible to delete because items have been sold.");
				}

				//Not enough goods in the warehouse
				LocationType warehouseType = locationTypeDao.getWarehouseType();
				Location warehouse = locationDao.get(warehouseType).get(0);
				ProductType type = entity.getProductType();
				Stock stock = stockDao.get(warehouse, type);
				if (stock == null) {
					throw new BusinessException(
						"Some shipments are impossible to delete because there are not enought goods in the warehouse");
				} else {
					int availableQty = stock.getQuantity();
					int shipmentQty = entity.getInitialQuantity();
					if (availableQty < shipmentQty) {
						throw new BusinessException(
							"Some shipments are impossible to delete because there are not enought goods in the warehouse");
					} else {

						int remainingQty = availableQty - shipmentQty;
						if(remainingQty == 0){
							//Delete empty warehouse stock
							stockDao.delete(stock);
						} else{
							stock.setQuantity(remainingQty);
							stockDao.merge(stock);
						}
						
						//Update debt
						Invoice invoice = entity.getInvoice();
						BigDecimal newDebt = new BigDecimal(0);
						BigDecimal unitPrice = entity.getUnitPrice();
						
						//If shipment was paid, remove its total price from the debt
						if(Invoice.IMMEDIATE_PAY.equals(invoice.getPaymentType())){
							BigDecimal shipmentPrice = unitPrice.multiply(new BigDecimal(entity.getInitialQuantity()));
							newDebt = invoice.getRestToPay().subtract(shipmentPrice);							
						} 
						//If shipment is paid on sale, remove the debt corresponding to the sold quantity
						else if(Invoice.ONSALE_PAY.equals(invoice.getPaymentType())){
							int soldQty = entity.getInitialQuantity() - entity.getCurrentQuantity();
							BigDecimal price = unitPrice.multiply(new BigDecimal(soldQty));
							newDebt = invoice.getRestToPay().subtract(price);
						}
						//set debt to 0 if negative
						if(newDebt.compareTo(new BigDecimal(0)) == -1){
							invoice.setRestToPay(new BigDecimal(0));
						} else{
							invoice.setRestToPay(newDebt);
						}
						
						//Merge invoice and delete shipment
						invoiceDao.merge(invoice);
						shipmentDao.delete(entity);
					}
				}
			}
		}
	}
}
