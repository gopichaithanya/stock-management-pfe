package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;

/**
 * Displays a list of product types
 * 
 * @author Alexandra
 *
 */
public interface ProductTypeListView extends IsWidget {

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
	public ListStore<ProductType> getData();

	/**
	 * Clears the view components
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
	 * Deletes type from the displayed list
	 * 
	 * @param productType
	 */
	public void deleteData(ProductType productType);
	
	/**
	 * Get the window for product type creation
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
	
	
	/**
	 * Set loader for paging
	 * 
	 * @param loader
	 */
	public void setLoader(
			PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductType>> loader);
	
	
	/**
	 * Bind paging toolbar with grid
	 * 
	 */
	public void bindPagingToolBar();

	/**
	 * Refresh the grid view
	 * 
	 */
	public void refreshGrid();

	public void maskGrid();
	
	public void unmaskGrid();

}
