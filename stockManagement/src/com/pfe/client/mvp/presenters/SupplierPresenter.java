package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.SupplierDTO;

/**
 * Controls the views displaying supplier related information
 * 
 * @author Alexandra
 *
 */
public interface SupplierPresenter extends Presenter {
	
	/**
	 * Goes to a new place
	 * 
	 * @param place
	 */
	public void goTo(Place place);
	
	/**
	 * Calls RPC service to add new supplier
	 * 
	 * @param supplier
	 */
	public void create(SupplierDTO supplier);
	
	/**
	 * Retrieves supplier by id
	 * 
	 * @param id
	 */
	public void find(Long id);
	
	/**
	 * Retrieves all suppliers from database
	 * 
	 */
	public void getAll();

	
	/**
	 * Updates supplier
	 * 
	 * @param updatedSupplier
	 */
	public void update(SupplierDTO updatedSupplier);

	/***
	 * Deletes supplier
	 * 
	 * @param supplier
	 */
	public void delete(SupplierDTO supplier);

	/**
	 * Filters list by name. Creates new paging load configuration corresponding
	 * to the filtered data
	 * 
	 * @param name
	 */
	public void filter(String name);

	/**
	 * Clears filters. Loads pages without filters
	 * 
	 */
	public void clearFilter();

	/**
	 * Loads selected type data in details panel
	 * 
	 * @param supplier
	 */
	public void displayDetailsView(SupplierDTO supplier);

	
	/**
	 * Updates invoice via corresponding supplier
	 * 
	 * @param updatedInvoice
	 */
	public void updateInvoice(InvoiceDTO updatedInvoice);
	
	
	/**
	 * Retrieves all product types from server
	 * 
	 */
	public void getProductTypes();
}
