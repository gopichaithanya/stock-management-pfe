package com.pfe.client.mvp.views;

import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.StockDTO;

/**
 * Contains UI components displaying informations on the location : name, type, list of stocks
 * 
 * @author Alexandra
 *
 */
public interface EditLocationView {

	
	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(LocationPresenter presenter);
	
	/**
	 * Sets the view data
	 * 
	 * @param location
	 */
	public void setData(LocationDTO location);
	
	/**
	 * Clears the UI components
	 * 
	 */
	public void clearData();
	
	/**
	 * Removes stock from location list
	 * 
	 * @param stock
	 */
	public void deleteData(StockDTO stock);
	
	/**
	 * Updates stock
	 * 
	 * @param stock
	 */
	public void updateData(StockDTO stock);
	
	/**
	 * Shows window
	 */
	public void show();

	/**
	 * Hides window
	 */
	public void hide();
	
}
