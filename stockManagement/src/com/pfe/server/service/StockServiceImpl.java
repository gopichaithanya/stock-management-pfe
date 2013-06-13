package com.pfe.server.service;

import java.math.BigDecimal;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.StockService;
import com.pfe.server.dao.invoice.InvoiceDao;
import com.pfe.server.dao.shipment.ShipmentDao;
import com.pfe.server.dao.stock.StockDAO;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.StockDTO;
import com.pfe.shared.model.Invoice;
import com.pfe.shared.model.ProductType;
import com.pfe.shared.model.Shipment;
import com.pfe.shared.model.Stock;

@Service("stockService") 
public class StockServiceImpl implements StockService {
	
	@Autowired
	private StockDAO stockDao;
	@Autowired
	private ShipmentDao shipmentDao;
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public StockDTO sell(StockDTO stock, int quantity) throws BusinessException {
		
		Stock entity = dozerMapper.map(stock, Stock.class);
		ProductType type = entity.getType();
		int retrievedQty = 0;
		int start = 0;
		
		//Retrieve shipments until we have the required to sell
		while(retrievedQty < quantity){
			
			List<Shipment> shipments = shipmentDao.search(start, 1, type);
			if(shipments.size() == 0){
				throw new BusinessException("Not enough goods.");
			} 
			Shipment shipment = shipments.get(0);
			
			int quantityFromShipment = 0;
			if((retrievedQty + shipment.getCurrentQuantity()) <= quantity){
				//All remaining items in the shipment will be retrieved
				//Set shipment quantity to 0
				quantityFromShipment = shipment.getCurrentQuantity();
				shipment.setCurrentQuantity(0);
			} 
			else{
				//Only a part of the items in the shipment will be retrieved
				//Compute the needed quantity
				quantityFromShipment = quantity - retrievedQty;
				shipment.setCurrentQuantity(shipment.getCurrentQuantity() - quantityFromShipment);
			}
			//Update retrieved quantity
			retrievedQty += quantityFromShipment;
			
			//Update debt
			if(!shipment.getPaid()){
				//If shipment is not paid, add to its invoice debt
				Invoice invoice = shipment.getInvoice();
				BigDecimal price = new BigDecimal(quantityFromShipment).multiply(shipment.getUnitPrice());
				invoice.setRestToPay(invoice.getRestToPay().add(price));
				invoiceDao.merge(invoice);
			}
			
			shipmentDao.merge(shipment);
			
			//Increase start index to retrieve the next shipment
			start++;
		}
		
		int newStockQuantity = entity.getQuantity() - quantity;
		if(newStockQuantity == 0){
			stockDao.delete(entity);
			return null;
		}
		entity.setQuantity(newStockQuantity);
		Stock merged = stockDao.merge(entity);
		
		return dozerMapper.map(merged, StockDTO.class);
	}

}
