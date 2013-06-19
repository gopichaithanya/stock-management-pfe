package com.pfe.client.mvp.views;
import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.shared.dto.LocationDTO;

/**
 * Display detailed information on selected location with no possibility of
 * editing.
 * 
 * @author Alexandra
 * 
 */
public interface LocationDetailView extends IsWidget {

	/**
	 * Clears the view data
	 */
	public void clearData();

	/**
	 * Sets data to display
	 * 
	 * @param data
	 */
	public void setData(LocationDTO data);

}
