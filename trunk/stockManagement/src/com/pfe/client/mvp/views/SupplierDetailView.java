package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.shared.dto.SupplierDto;

/**
 * Contains components used to display detailed information on selected supplier
 * 
 * @author Alexandra
 * 
 */
public interface SupplierDetailView extends IsWidget {

	/**
	 * Clears the view data
	 */
	public void clearData();

	/**
	 * Sets data to display
	 * 
	 * @param supplier
	 */
	public void setData(SupplierDto supplier);

}
