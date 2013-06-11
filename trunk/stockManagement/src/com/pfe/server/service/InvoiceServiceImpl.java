package com.pfe.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.client.service.InvoiceService;
import com.pfe.server.dao.invoice.InvoiceDao;
import com.pfe.server.dao.location.LocationDAO;
import com.pfe.server.dao.locationtype.LocationTypeDAO;
import com.pfe.server.dao.shipment.ShipmentDao;
import com.pfe.server.dao.stock.StockDAO;
import com.pfe.server.dao.supplier.SupplierDao;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.ShipmentDTO;
import com.pfe.shared.model.Invoice;
import com.pfe.shared.model.Location;
import com.pfe.shared.model.LocationType;
import com.pfe.shared.model.ProductType;
import com.pfe.shared.model.Shipment;
import com.pfe.shared.model.Stock;
import com.pfe.shared.model.Supplier;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@Service("invoiceService") 
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private ShipmentDao shipmentDao;
	@Autowired
	private LocationDAO locationDao;
	@Autowired
	private LocationTypeDAO locationTypeDao;
	@Autowired
	private StockDAO stockDao;
	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public InvoiceDTO update(InvoiceDTO updatedInvoice) throws BusinessException {
				
		Long id = updatedInvoice.getId();
		Invoice invoice = invoiceDao.get(id);
		
		//Supplier update
		Long initialId = invoice.getSupplier().getId();
		Long updatedId = updatedInvoice.getSupplier().getId();
		if(!initialId.equals(updatedId)){
			
			Supplier updatedSupplier = supplierDao.get(updatedId);
			invoice.setSupplier(updatedSupplier);
			updatedSupplier.getInvoices().add(invoice);
			
			Supplier initialSupplier = invoice.getSupplier();
			initialSupplier.getInvoices().remove(invoice);
		
			supplierDao.merge(initialSupplier);
			supplierDao.merge(updatedSupplier);
		}
		
		//Debt update
		BigDecimal updatedDebt = new BigDecimal(updatedInvoice.getRestToPay());
		invoice.setRestToPay(updatedDebt);
		
		//Date update
		invoice.setCreated(updatedInvoice.getCreated());
		
		//Shipments update
		List<ShipmentDTO> shipmentDtos = updatedInvoice.getShipments();
		List<Shipment> shipments = new ArrayList<Shipment>();
		for(ShipmentDTO shipmentDto : shipmentDtos){
			Shipment shipment = dozerMapper.map(shipmentDto, Shipment.class);
			
			//Shipment is new
			if(shipment.getId() == null){
				shipment.setCurrentQuantity(shipment.getInitialQuantity());
			} 
			//Shipment is updated
			else{
				manageShipmentExceptions(shipment);
			}
			
			updateStocks(shipment);
			shipment.setCreated(invoice.getCreated());
			if(Invoice.IMMEDIATE_PAY.equals(invoice.getPaymentType())){
				shipment.setPaid(true);
			} else{
				shipment.setPaid(false);
			}
			
			shipment.setInvoice(invoice);
			shipments.add(shipment);
			
			//Update debt
			BigDecimal debtDifference = getDebtDifference(shipment);
			invoice.setRestToPay(invoice.getRestToPay().add(debtDifference));
		}
		invoice.setShipments(shipments);
		invoiceDao.merge(invoice);	
		InvoiceDTO dto = dozerMapper.map(invoice, InvoiceDTO.class, "fullInvoice");
		return dto;
	}

	/**
	 * Checks for business exceptions when updating a shipment. Exceptions may
	 * occur when updating the shipment's initial quantity or product type.
	 * 
	 * @param updatedShipment
	 * @throws BusinessException
	 */
	private void manageShipmentExceptions(Shipment updatedShipment) throws BusinessException{
		
		//Retrieve original shipment form database
		Shipment initialShipment = shipmentDao.get(updatedShipment.getId());
		int removedQty = 0;
		
		//Shipment type was changed 
		if(!initialShipment.getProductType().getId().equals(updatedShipment.getProductType().getId())){
			
			//Can't change type for sold items
			if(initialShipment.getCurrentQuantity() != initialShipment.getInitialQuantity()){
				throw new BusinessException("Cannot change type of shipment because" +
						" part of its items have already been sold.");
			} 
			//Shipments items are not sold
			else{
				removedQty = initialShipment.getInitialQuantity();
			}
		}
		
		//Shipment type was not changed
		else{
			
			//Current quantity greated than initial quantity
			if(initialShipment.getCurrentQuantity() > updatedShipment.getInitialQuantity()){
				throw new BusinessException("Cannot update shipment : initial quantity is inferior " +
						" to current quantity");
			}
			int initial = initialShipment.getInitialQuantity();
			int updated = updatedShipment.getInitialQuantity();
			
			if(initial > updated){
				removedQty = initial - updated;
			}
		}
		
		//Check if we can remove quantity from stocks
		if(removedQty > 0){
			LocationType warehouseType = locationTypeDao.getWarehouseType();
			Location warehouse = locationDao.get(warehouseType).get(0);
			Stock stock = stockDao.get(warehouse, initialShipment.getProductType());
			if(stock == null){
				throw new BusinessException("Cannot update shipment : not enough goods to remove from the" +
						" warehouse");
			}
			else if(stock.getQuantity() < removedQty){
				throw new BusinessException("Cannot update shipment : not enough goods to remove from the" +
						" warehouse");
			}
		}
		
	}
	
	/**
	 * Updates stocks when adding or updating a shipment
	 * 
	 * @param shipment
	 */
	private void updateStocks(Shipment shipment){
		
		LocationType warehouseType = locationTypeDao.getWarehouseType();
		Location warehouse = locationDao.get(warehouseType).get(0);
		Stock stock = stockDao.get(warehouse, shipment.getProductType());
		
		//Create stock of given type if it doesn't exist
		if(stock == null){
			stock = new Stock();
			stock.setLocation(warehouse);
			stock.setType(shipment.getProductType());
			stock.setQuantity(0);
			warehouse.getStocks().add(stock);
		}
		
		int updatedQty;
		
		//Shipment is new. Add its quantity to the stock
		if(shipment.getId() == null){
			updatedQty = stock.getQuantity() + shipment.getInitialQuantity();
			stock.setQuantity(updatedQty);
		}
		
		//Shipment is updated
		else{
			Shipment initialShipment = shipmentDao.get(shipment.getId());
			
			ProductType initialType = initialShipment.getProductType();
			ProductType updatedType = shipment.getProductType();
			//Shipment type was changed
			if(!initialType.getId().equals(updatedType.getId())){
				
				//Remove ancient type items from stocks
				Stock ancientStock = stockDao.get(warehouse, initialType);
				updatedQty =  ancientStock.getQuantity() - initialShipment.getInitialQuantity();
				ancientStock.setQuantity(updatedQty);
				stockDao.merge(ancientStock);
				
				//Add new quantity in stock
				updatedQty = stock.getQuantity() + shipment.getInitialQuantity();
				stock.setQuantity(updatedQty);
			} 
			//Shipment type was not changed
			else{
				updatedQty = stock.getQuantity() - initialShipment.getInitialQuantity() + shipment.getInitialQuantity();
				stock.setQuantity(updatedQty);
			}
		}
		locationDao.merge(warehouse);
	}
	
	/**
	 * Calculates debt difference to add to the invoice when updating or adding
	 * a shipment
	 * 
	 * @param shipment
	 * @return sum to add to invoice debt
	 */
	private BigDecimal getDebtDifference(Shipment shipment){
		
		//Compute shipment total price
		BigDecimal debt = new BigDecimal(shipment.getInitialQuantity()).multiply(shipment.getUnitPrice());
		
		//For a new shipment add its price to the invoice debt
		if(shipment.getId() == null){
			return debt;
		} 
		//For an updated shipment compute new debt minus old debt 
		else{
			Shipment initialShipment = shipmentDao.get(shipment.getId());
			BigDecimal initialDebt = new BigDecimal(initialShipment.getInitialQuantity()).
					multiply(initialShipment.getUnitPrice());
			return debt.subtract(initialDebt);
		}

	}

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public PagingLoadResult<InvoiceDTO> search(FilterPagingLoadConfig config) {
		
		int size = (int) invoiceDao.count();
		int start = config.getOffset();
		int limit = config.getLimit();
		List<Invoice> sublist = invoiceDao.search(start, limit);
		List<InvoiceDTO> dtos = new ArrayList<InvoiceDTO>();

		if (sublist.size() > 0) {
			for (Invoice invoice : sublist) {
				dtos.add(dozerMapper.map(invoice, InvoiceDTO.class, "miniInvoice"));
			}
		}
		return new PagingLoadResultBean<InvoiceDTO>(dtos, size, config.getOffset());
	}
}
