package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Contains components used to display detailed information on selected type
 * 
 * @author Alexandra
 *
 */
public interface ProductTypeDetailView extends IsWidget {
	
	
	/**
	 * Clears the view data
	 */
	public void clearData();
	
	/**
	 * Sets data to display
	 * 
	 * @param String
	 */
	public void setData(String data);
}
