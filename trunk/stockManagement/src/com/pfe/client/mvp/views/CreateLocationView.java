package com.pfe.client.mvp.views;

import java.util.List;

import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.shared.dto.LocationTypeDTO;

/**
 * Contains UI components for setting location name and type
 * 
 * @author Alexandra
 *
 */
public interface CreateLocationView {

	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(LocationPresenter presenter);
	
	/**
	 * Loads available types for combo box
	 * 
	 * @param locations
	 */
	public void setLocationTypes(List<LocationTypeDTO> locations);
	
	/**
	 * Shows window
	 */
	public void show();

	/**
	 * Hides window
	 */
	public void hide();
}
