package com.pfe.client.mvp;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.pfe.client.mvp.views.ProductTypesView;
import com.pfe.client.service.ProductTypeServiceAsync;

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
	public ProductTypesView getProductTypesView();


	// SERVICES

	/**
	 * Creates the RPC product type service
	 * 
	 * @return
	 */
	public ProductTypeServiceAsync getProductTypeService();

}
