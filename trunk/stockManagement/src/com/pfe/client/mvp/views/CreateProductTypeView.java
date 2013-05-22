package com.pfe.client.mvp.views;

import com.pfe.client.mvp.presenters.ProductTypesPresenter;

public interface CreateProductTypeView {

	
	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(ProductTypesPresenter presenter);
	
	
	/**
	 * Clears the view data
	 */
	public void clearData();
}
