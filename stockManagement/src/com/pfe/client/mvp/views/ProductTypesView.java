package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.shared.model.ProductType;

/**
 * Displays the gird of product types
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
	public void setPresenter(ProductTypePresenter presenter);


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
	
	/**
	 * Adds new line in the list
	 */
	public void addData(ProductType productType);
	
	/**
	 * Updates a line in the list
	 * 
	 * @param productType
	 */
	public void updateData(ProductType productType);
	
	
	/**
	 * Get create product type window
	 * 
	 * @return
	 */
	public CreateProductTypeViewImpl getCreateWindow();

	/**
	 * Get edit window
	 * 
	 * @return
	 */
	public EditProductTypeViewImpl getEditWindow();
}
