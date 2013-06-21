package com.pfe.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.StockService;
import com.pfe.server.dao.invoice.InvoiceDao;
import com.pfe.server.dao.location.LocationDAO;
import com.pfe.server.dao.shipment.ShipmentDao;
import com.pfe.server.dao.stock.StockDAO;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.StockDTO;
import com.pfe.shared.model.Invoice;
import com.pfe.shared.model.Location;
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
	private LocationDAO locationDao;
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
			
			//TODO retrieve them ordered by date
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
				
				if(shipment.getCurrentQuantity() == 0){
					shipment.setPaid(true);
				}
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public StockDTO ship(StockDTO stock, int quantity, LocationDTO destination) throws BusinessException {
		
		if(quantity > stock.getQuantity()){
			throw new BusinessException("Not enough goods in the stock.");
		}
		
		Stock sourceStock = dozerMapper.map(stock, Stock.class);
		ProductType type = sourceStock.getType();
		
		Location destinationEntity = locationDao.get(destination.getId());
		Stock destinationStock = stockDao.get(destinationEntity, type);
		if(destinationStock == null){
			//Create stock 
			destinationStock = new Stock();
			destinationStock.setLocation(destinationEntity);
			destinationStock.setType(type);
			destinationStock.setQuantity(0);
		}
		
		destinationStock.setQuantity(destinationStock.getQuantity() + quantity);
		int sourceQuantity = stock.getQuantity() - quantity;
	
		stockDao.merge(destinationStock);
	
		if(sourceQuantity == 0){
			//If all remaining items are moved, delete source stock
			stockDao.delete(sourceStock);
			return null;
		} else{
			sourceStock.setQuantity(sourceQuantity);
			Stock merged = stockDao.merge(sourceStock);
			return dozerMapper.map(merged, StockDTO.class);
		}

	}

	@Override
	public List<StockDTO> find(ProductTypeDTO productType) {
		
		ProductType type = dozerMapper.map(productType, ProductType.class);
		List<Stock> stocks = stockDao.get(type);
		List<StockDTO> dtos = new ArrayList<StockDTO>();
		for(Stock stock : stocks){
			dtos.add(dozerMapper.map(stock, StockDTO.class));
		}
		
		return dtos;
	}

}
