package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.StockDTO;

/**
 * Controls the views displaying location related information
 * 
 * @author Alexandra
 *
 */
public interface LocationPresenter extends Presenter {

	/**
	 * Goes to a new place
	 * 
	 * @param place
	 */
	public void goTo(Place place);
	
	/**
	 * Retrieves location by id
	 * 
	 * @param id
	 */
	public void find(Long id);
	
	/**
	 * Calls service to retrieve all available locations
	 * 
	 */
	public void getAll();
	
	/**
	 * Loads selected type data in details panel
	 * 
	 * @param location
	 */
	public void displayDetailsView(LocationDTO location);
	
	/**
	 * Calls service to sell the given quantity from stock
	 * 
	 * @param fromStock
	 * @param quantity
	 */
	public void sell(StockDTO fromStock, int quantity);
	
	/**
	 * Calls service to ship items from stock to another location
	 * 
	 * @param fromStock
	 * @param quantity
	 * @param toLocation
	 */
	public void ship(StockDTO fromStock, int quantity, LocationDTO toLocation);
	
}
