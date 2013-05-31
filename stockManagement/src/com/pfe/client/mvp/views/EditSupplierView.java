package com.pfe.client.mvp.views;

import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.shared.dto.SupplierDto;

/**
 * Contains UI components that display supplier information : name, description, invoices, debt
 * 
 * @author Alexandra
 *
 */

public interface EditSupplierView {

	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(SupplierPresenter presenter);
	
	/**
	 * Sets the view data
	 * 
	 * @param supplier
	 */
	public void setData(SupplierDto supplier);
	
	/**
	 * Clears the UI componentss
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
