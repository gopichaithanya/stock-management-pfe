package com.pfe.client.mvp;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.pfe.client.mvp.views.ProductTypeDetailView;
import com.pfe.client.mvp.views.ProductTypeListView;
import com.pfe.client.mvp.views.SupplierDetailView;
import com.pfe.client.mvp.views.SupplierListView;
import com.pfe.client.mvp.views.WelcomeView;
import com.pfe.client.service.InvoiceServiceAsync;
import com.pfe.client.service.ProductTypeServiceAsync;
import com.pfe.client.service.SupplierServiceAsync;

/**
 * Gives access to the application event bus, the views and the RPC services
 * 
 * @author Alexandra
 * 
 */
public interface ClientFactory {

	/**
	 * Initializes the application event bus
	 * 
	 * @return
	 */
	public EventBus getEventBus();

	/**
	 * Initializes the place controller
	 * 
	 * @return
	 */
	public PlaceController getPlaceController();

	// VIEWS
	
	public WelcomeView getWelcomeView();
	
	public ProductTypeListView getProductTypeListView();

	public ProductTypeDetailView getProductTypeDetailView();

	public SupplierListView getSupplierListView();
	
	public SupplierDetailView getSupplierDetailView();
	
	// SERVICES

	/**
	 * Creates the RPC service for operations on product types
	 * 
	 * @return
	 */
	public ProductTypeServiceAsync getProductTypeService();
	
	/**
	 * Creates the RPC service for operations on suppliers
	 * 
	 * @return
	 */
	public SupplierServiceAsync getSupplierService();
	
	/**
	 * Creates RPC service for operations on invoices
	 * 
	 * @return
	 */
	public InvoiceServiceAsync getInvoiceService();

}
