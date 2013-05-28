package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;

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
