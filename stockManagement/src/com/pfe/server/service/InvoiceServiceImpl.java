package com.pfe.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.pfe.server.model.Invoice;
import com.pfe.server.model.Location;
import com.pfe.server.model.LocationType;
import com.pfe.server.model.ProductType;
import com.pfe.server.model.Shipment;
import com.pfe.server.model.Stock;
import com.pfe.server.model.Supplier;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.ShipmentDTO;
import com.sencha.gxt.data.shared.loader.FilterConfig;
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
	
	public static final String FULL_INVOICE_MAPPING = "fullInvoice";
	public static final String MINI_INVOICE_MAPPING = "miniInvoice";

	@Override
	@Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
	public InvoiceDTO find(Long id) {
		Invoice invoice = invoiceDao.get(id);
		return dozerMapper.map(invoice, InvoiceDTO.class, FULL_INVOICE_MAPPING);
	}
	
	@Override
	public PagingLoadResult<InvoiceDTO> search(FilterPagingLoadConfig config) {
		
		int start = config.getOffset();
		int limit = config.getLimit();
		List<Invoice> sublist = new ArrayList<Invoice>();
		int size = 0;
		
		//Retrieve all invoices by default
		Boolean showAll = true;
		
		//Show all or unpaid invoices filter
		FilterConfig debtFilter = config.getFilters().get(0);
		if(debtFilter.getValue().equals("false")){
			//show only unpaid invoices
			showAll = false;
		}
		
		//Get filter value 
		FilterConfig codeFilter = config.getFilters().get(1);
		String filterValue = codeFilter.getValue();
		
		try{
			// Filter by code
			int invoiceCode = Integer.parseInt(filterValue); 
			
			size = (int) invoiceDao.countByCriteria(showAll, invoiceCode);
			sublist = invoiceDao.search(start, limit, showAll, invoiceCode);
		}
		
		catch (NumberFormatException ex){
			//Filter by supplier name
			if(StringUtils.isNotBlank(filterValue)){
				size = invoiceDao.countByCriteria(showAll, filterValue);
				
				List<Invoice> sublistShowAll = invoiceDao.search(start, limit, filterValue);
				if(!showAll){
					//Keep only unpaid invoices
					for(Invoice invoice : sublistShowAll){
						if(invoice.getRestToPay().compareTo(new BigDecimal(0)) == 1){
							sublist.add(invoice);
						}
					}
				} else {
					sublist.addAll(sublistShowAll);
				}
			
			} else {
				//Empty filter 
				size = (int) invoiceDao.countByCriteria(showAll, -1);
				sublist = invoiceDao.search(start, limit, showAll, -1);
			}
			
		}
		
		List<InvoiceDTO> dtos = new ArrayList<InvoiceDTO>();
		if (sublist.size() > 0) {
			for (Invoice invoice : sublist) {
				dtos.add(dozerMapper.map(invoice, InvoiceDTO.class, MINI_INVOICE_MAPPING));
			}
		}
		return new PagingLoadResultBean<InvoiceDTO>(dtos, size, config.getOffset());
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public InvoiceDTO create(InvoiceDTO invoice) throws BusinessException {

		Invoice entity = dozerMapper.map(invoice, Invoice.class, FULL_INVOICE_MAPPING);
		//Get current date time
		Date today = Calendar.getInstance().getTime();
		entity.setCreated(today);
		entity.setRestToPay(new BigDecimal(0));
		
		List<Shipment> shipments = entity.getShipments();
		for(Shipment shipment : shipments){
			shipment.setCreated(entity.getCreated());
			shipment.setCurrentQuantity(shipment.getInitialQuantity());
			
			if(Invoice.IMMEDIATE_PAY.equals(invoice.getPaymentType())){
				//Shipment is considered paid and debt is increased by its price
				shipment.setPaid(true);
				BigDecimal price = new BigDecimal(shipment.getInitialQuantity()).multiply(shipment.getUnitPrice());
				BigDecimal debt = entity.getRestToPay().add(price);
				entity.setRestToPay(debt);
			} else if(Invoice.ONSALE_PAY.equals(invoice.getPaymentType())){
				//Shipment is unpaid until sold and has no effect on debt when created
				shipment.setPaid(false);
			}
			
			updateStocks(shipment);
		}
		
		Invoice merged = invoiceDao.merge(entity);
		return dozerMapper.map(merged, InvoiceDTO.class, MINI_INVOICE_MAPPING);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public InvoiceDTO update(InvoiceDTO updatedInvoice) throws BusinessException {
		
		//We don't allow update on code, creation date or payment type
		Long id = updatedInvoice.getId();
		Invoice invoice = invoiceDao.get(id);
		
		//Debt update
		BigDecimal updatedDebt = new BigDecimal(updatedInvoice.getRestToPay());
		invoice.setRestToPay(updatedDebt);
		
		//Supplier update
		Supplier updatedSupplier = dozerMapper.map(updatedInvoice.getSupplier(), Supplier.class);
		invoice.setSupplier(updatedSupplier);
		
		//Shipments update
		List<ShipmentDTO> shipmentDtos = updatedInvoice.getShipments();
		List<Shipment> shipments = new ArrayList<Shipment>();
		for(ShipmentDTO shipmentDto : shipmentDtos){
			Shipment shipment = dozerMapper.map(shipmentDto, Shipment.class);
			
			//Shipment is new
			if(shipment.getId() == null){
				shipment.setCurrentQuantity(shipment.getInitialQuantity());
				
				if(Invoice.IMMEDIATE_PAY.equals(invoice.getPaymentType())){
					//Shipment is considered paid and debt is increased by its price
					shipment.setPaid(true);
					BigDecimal price = new BigDecimal(shipment.getInitialQuantity()).multiply(shipment.getUnitPrice());
					BigDecimal debt = invoice.getRestToPay().add(price);
					invoice.setRestToPay(debt);
				} else if(Invoice.ONSALE_PAY.equals(invoice.getPaymentType())){
					//Shipment is unpaid until sold and has no effect on debt when created
					shipment.setPaid(false);
				}
				
			} 
			//Shipment is updated
			else{
				//Retrieve original shipment form database
				Shipment initialShipment = shipmentDao.get(shipment.getId());
				manageShipmentExceptions(shipment, initialShipment);
				int initialQty = initialShipment.getInitialQuantity();
				int updatedQty =  shipment.getInitialQuantity();
				if(initialQty < updatedQty){
					//add quantity to current quantity
					int surplus = updatedQty - initialQty;
					shipment.setCurrentQuantity(shipment.getCurrentQuantity() + surplus);
				} else if(initialQty > updatedQty){
					int removed = initialQty - updatedQty;
					shipment.setCurrentQuantity(shipment.getCurrentQuantity() - removed);
				}
				
				if(Invoice.IMMEDIATE_PAY.equals(invoice.getPaymentType())){
					//Shipment is paid but we have to recompute the debt
					Shipment initial = shipmentDao.get(shipment.getId());
					BigDecimal initialPrice = new BigDecimal(initial.getInitialQuantity()).multiply(initial.getUnitPrice());
					BigDecimal currentPrice = new BigDecimal(shipment.getInitialQuantity()).multiply(shipment.getUnitPrice());
					BigDecimal debt = invoice.getRestToPay().subtract(initialPrice).add(currentPrice);
					invoice.setRestToPay(debt);
					
				} else if(Invoice.ONSALE_PAY.equals(invoice.getPaymentType())){
					//For invoices paid on sale, a shipment is considered paid
					//only when its current quantity is 0 (all items are sold)
					if(shipment.getCurrentQuantity() > 0){
						shipment.setPaid(false);
					} else
						shipment.setPaid(true);
				}
			}
			
			updateStocks(shipment);
			shipment.setCreated(invoice.getCreated());
			shipment.setInvoice(invoice);
			shipments.add(shipment);
			
		}
		invoice.setShipments(shipments);
		Invoice merged = invoiceDao.merge(invoice);	
		InvoiceDTO dto = dozerMapper.map(merged, InvoiceDTO.class, FULL_INVOICE_MAPPING);
		return dto;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(InvoiceDTO invoice) throws BusinessException {
		
		Invoice entity = invoiceDao.get(invoice.getId());
		List<Shipment> shipments = entity.getShipments();
		if(shipments.size() > 0){
			LocationType warehouseType = locationTypeDao.getWarehouseType();
			Location warehouse = locationDao.get(warehouseType).get(0);
			
			for(Shipment shipment : shipments){
				if(shipment.getCurrentQuantity() != shipment.getInitialQuantity()){
					throw new BusinessException("Invoice cannot be deleted : some items have been sold.");
				}
				Stock stock = stockDao.get(warehouse, shipment.getProductType());
				int shipmentQty = shipment.getInitialQuantity();
				if(stock == null || stock.getQuantity() < shipmentQty){
					throw new BusinessException("Invoice cannot be deleted : not enough goods to remove from the" +
							" warehouse");
				}
				
				//Compute quantity after removing shipment
				int updatedQty = stock.getQuantity() - shipmentQty;
				if(updatedQty == 0){
					//Delete stock if empty
					stockDao.delete(stock);
				} else{
					stock.setQuantity(updatedQty);
					stockDao.merge(stock);
				}
				
				shipmentDao.delete(shipment);
				
			}
		}
		Supplier supplier = entity.getSupplier();
		supplier.getInvoices().remove(entity);
		supplierDao.merge(supplier);
		invoiceDao.delete(entity);
	}

	/**
	 * Checks for business exceptions when updating a shipment. Exceptions may
	 * occur when updating the shipment's initial quantity or product type.
	 * 
	 * @param updatedShipment object after update in GUI
	 * @param initialShipment object as it is in the database
	 * @throws BusinessException
	 */
	private void manageShipmentExceptions(Shipment updatedShipment, Shipment initialShipment) throws BusinessException{
		
		int removedQty = 0;
		
		//Shipment type was changed 
		if(!initialShipment.getProductType().getId().equals(updatedShipment.getProductType().getId())){
			
			//Can't change type for sold items
			if(initialShipment.getCurrentQuantity() != initialShipment.getInitialQuantity()){
				throw new BusinessException("Cannot change type of shipment : some items have been sold.");
			} 
			//Shipments items are not sold
			else{
				removedQty = initialShipment.getInitialQuantity();
			}
		}
		
		//Shipment type was not changed
		else{
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
			if(stock == null || stock.getQuantity() < removedQty){
				throw new BusinessException("Cannot update shipment : not enough goods to remove from warehouse.");
			}
		}
		
	}
	
	/**
	 * Updates stocks when adding or updating a shipment. Only warehouse stocks may be changed.
	 * 
	 * @param shipment
	 */
	private void updateStocks(Shipment shipment){
		
		LocationType warehouseType = locationTypeDao.getWarehouseType();
		Location warehouse = locationDao.get(warehouseType).get(0);
		//Get warehouse stock corresponding to the shipment type
		Stock stock = stockDao.get(warehouse, shipment.getProductType());
		
		//Initialize stock of given type if it doesn't exist
		if(stock == null){
			stock = new Stock();
			stock.setLocation(warehouse);
			stock.setType(shipment.getProductType());
			stock.setQuantity(0);
		}
		
		int updatedQty; 
		
		//If shipment is new then add its quantity to the stock
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
				//Remove ancient stock if empty
				if(updatedQty == 0){
					stockDao.delete(ancientStock);
				} else{
					ancientStock.setQuantity(updatedQty);
					stockDao.merge(ancientStock);
				}
				
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
		
		if(stock.getQuantity() > 0){
			stockDao.merge(stock);
		} else if(stock.getQuantity() == 0 && stock.getId() != null){
			stockDao.delete(stock);
		}
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(List<InvoiceDTO> invoices) throws BusinessException {
		for(InvoiceDTO invoice : invoices){
			delete(invoice);
		}
		
	}
}
