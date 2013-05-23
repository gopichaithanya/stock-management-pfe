package com.pfe.client.mvp.views;

import com.pfe.client.mvp.presenters.ProductTypePresenter;

/**
 * Contains UI components for name and product type description
 * 
 * @author Alexandra
 *
 */
public interface CreateProductTypeView {

	
	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(ProductTypePresenter presenter);
	
	
	/**
	 * Clears the view data
	 */
	public void clearData();
}
