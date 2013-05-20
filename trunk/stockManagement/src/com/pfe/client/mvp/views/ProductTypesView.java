package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.ProductTypesPresenter;
import com.pfe.shared.model.ProductType;

/**
 * Contains UI components whose values are updated by the presenter to display
 * the list of available product types
 * 
 * @author Alexandra
 *
 */
public interface ProductTypesView extends IsWidget {

	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(ProductTypesPresenter presenter);


	/**
	 * Sets data to be rendered
	 * 
	 * @param productTypes
	 */
	public void setData(List<ProductType> productTypes);

	/**
	 * Gets the view data
	 * 
	 * @return
	 */
	public List<ProductType> getData();

	/**
	 * Clears the view data
	 */
	public void clearData();
}
