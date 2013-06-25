package com.pfe.client.mvp.presenters;

import java.util.List;

import com.google.gwt.place.shared.Place;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.ShipmentDTO;


/**
 * Controls the views displaying invoice related information
 * 
 * @author Alexandra
 */
public interface InvoicePresenter extends Presenter {
	
	/**
	 * Goes to a new place
	 * 
	 * @param place
	 */
	public void goTo(Place place);
	
	/**
	 * Calls RPC service to add new invoice
	 * 
	 * @param invoice
	 */
	public void create(InvoiceDTO invoice);
	
	/**
	 * Retrieves invoice by id
	 * 
	 * @param id
	 */
	public void find(Long id);
	
	/**
	 * Calls update service
	 * 
	 * @param updatedInvoice
	 */
	public void update(InvoiceDTO updatedInvoice);
	
	/**
	 * Calls delete for a list of invoices
	 * 
	 * @param invoices
	 */
	public void delete(List<InvoiceDTO> invoices);
	
	/**
	 * Retrieves available types from database
	 * 
	 * @param window receiving data : edit or create invoice
	 */
	public void getProductTypes(String window);
	
	/**
	 * Retrieves all suppliers
	 * 
	 * @param window receiving data : edit or create invoice
	 */
	public void getSuppliers(String window);
	
	/**
	 * Deletes shipments
	 * 
	 * @param shipments
	 */
	public void deleteShipments(List<ShipmentDTO> shipments);

	
	/**
	 * Sets paging parameters and loads list pages for all invoices. Takes into
	 * account the check box value to retrieve all or only unpaid invoices and
	 * the filter value
	 * 
	 */
	public void search();
}

