package com.pfe.client.mvp.presenters;

import java.util.List;

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
	 * Calls service to retrieve location by id, including its stocks
	 * 
	 * @param id
	 */
	public void find(Long id);

	/**
	 * Calls service to retrieve all available locations. Stock information is
	 * not retrieved.
	 * 
	 */
	public void getAll();
	
	/**
	 * Sets paging parameters, processes filter value and loads locations  
	 * 
	 */
	public void search();

	/**
	 * Adds location
	 * 
	 * @param location
	 */
	public void create(LocationDTO location);

	/**
	 * Calls service to remove locations from database
	 * 
	 * @param locations
	 */
	public void delete(List<LocationDTO> locations);

	/**
	 * Calls service to retrieve all location types and adds data to the caller
	 * window (create or edit location window)
	 * 
	 */
	public void getLocationTypes(String window);

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
	
	/**
	 * Calls service to retrieve location stocks with type name like given parameter.
	 * 
	 * @param productTypeName
	 * @param location
	 */
	public void searchStocks(String productTypeName, LocationDTO location);

}
