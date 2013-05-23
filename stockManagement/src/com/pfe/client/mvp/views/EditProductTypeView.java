package com.pfe.client.mvp.views;

import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.shared.model.ProductType;

public interface EditProductTypeView {

	
	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(ProductTypePresenter presenter);
	
	/**
	 * Sets the view data
	 * 
	 * @param productType
	 */
	public void setData(ProductType productType);
	
}
