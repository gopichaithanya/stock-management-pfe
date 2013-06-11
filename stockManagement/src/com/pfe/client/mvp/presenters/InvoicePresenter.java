package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;
import com.pfe.shared.dto.InvoiceDTO;


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
	 * Calls delete service
	 * 
	 * @param invoice
	 */
	public void delete(InvoiceDTO invoice);
	
	/**
	 * Retrieves available types from database
	 * 
	 */
	public void getProductTypes();
	
	/**
	 * Retrieves all suppliers
	 * 
	 */
	public void getSuppliers();
	
}
