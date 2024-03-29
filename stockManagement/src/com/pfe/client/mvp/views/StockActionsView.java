package com.pfe.client.mvp.views;

import java.util.List;

import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.StockDTO;

/**
 * This view displays location stocks and allows the user to sell or ship
 * products to other locations
 * 
 * @author Alexandra
 * 
 */
public interface StockActionsView {
	
	
	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(LocationPresenter presenter);
	
	/**
	 * Sets the displayed location and the list of stocks.
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
	 * Set available locations for shipping products
	 * 
	 * @param locations
	 */
	public void setLocations(List<LocationDTO> locations);
	
	/**
	 * Sets location stocks after a filter action
	 * 
	 * @param stocks
	 */
	public void setStocks(List<StockDTO> stocks);
	
	/**
	 * Shows window
	 */
	public void show();

	/**
	 * Hides window
	 */
	public void hide();

}
