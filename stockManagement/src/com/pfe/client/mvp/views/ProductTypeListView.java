package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.shared.dto.ProductTypeDTO;
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
	public void setData(List<ProductTypeDTO> productTypes);

	/**
	 * Gets the view data
	 * 
	 * @return
	 */
	public ListStore<ProductTypeDTO> getData();

	/**
	 * Clears the view components
	 */
	public void clearData();
	
	/**
	 * Adds new line in the list
	 */
	public void addData(ProductTypeDTO productType);
	
	/**
	 * Updates a line in the list
	 * 
	 * @param productType
	 */
	public void updateData(ProductTypeDTO productType);
	
	/**
	 * Deletes types from the displayed list
	 * 
	 * @param productType
	 */
	public void deleteData(List<ProductTypeDTO> productTypes);
	
	/**
	 * Get the window for product type creation
	 * 
	 * @return
	 */
	public CreateProductTypeView getCreateView();

	/**
	 * Get edit window
	 * 
	 * @return
	 */
	public EditProductTypeView getEditView();
	
	
	/**
	 * Set loader for paging
	 * 
	 * @param loader
	 */
	public void setPagingLoader(PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductTypeDTO>> loader);
	
	/**
	 * Gets filter value 
	 * 
	 * @return
	 */
	public String getFilterValue();
	

	/**
	 * Refresh the grid view
	 * 
	 */
	public void refreshGrid();

	public void maskGrid();
	
	public void unmaskGrid();

}
