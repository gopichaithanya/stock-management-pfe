package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;
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
	 * Calls service to sell the given quantity from stock
	 * 
	 * @param stock
	 * @param quantity
	 */
	public void sell(StockDTO stock, int quantity);
	
	/**
	 * Calls service to retrieve all available locations
	 * 
	 */
	public void getAll();
	
}
