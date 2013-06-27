package com.pfe.client.mvp.views;

import java.util.List;

import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.LocationTypeDTO;

/**
 * Used to display or update the name and type of the selected locations.
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
	 * Loads available types for combo box. The warehouse type is removed from
	 * the list because warehouse creation is not allowed.
	 * 
	 * @param locations
	 */
	public void setLocationTypes(List<LocationTypeDTO> locations);

	/**
	 * Clears the UI components
	 * 
	 */
	public void clearData();

	/**
	 * Shows window
	 */
	public void show();

	/**
	 * Hides window
	 */
	public void hide();

}
