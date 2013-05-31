package com.pfe.client.mvp.views;

import com.pfe.client.mvp.presenters.SupplierPresenter;

/**
 * Contains UI components for supplier name and description
 * 
 * @author Alexandra
 *
 */
public interface CreateSupplierView {
	
	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(SupplierPresenter presenter);
	
	
	/**
	 * Clears the view data
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
