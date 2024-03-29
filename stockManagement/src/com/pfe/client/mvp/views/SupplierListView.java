package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;

/**
 * Contains UI components to display a list of suppliers
 * 
 * @author Alexandra
 * 
 */
public interface SupplierListView extends IsWidget {

	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(SupplierPresenter presenter);

	/**
	 * Sets data to be rendered
	 * 
	 * @param suppliers
	 */
	public void setData(List<SupplierDTO> suppliers);

	/**
	 * Gets the view data
	 * 
	 * @return
	 */
	public ListStore<SupplierDTO> getData();

	/**
	 * Clears the view components
	 */
	public void clearData();

	/**
	 * Adds new line in the list
	 * 
	 * @param supplier
	 */
	public void addData(SupplierDTO supplier);

	/**
	 * Updates a line in the list
	 * 
	 * @param supplier
	 */
	public void updateData(SupplierDTO supplier);

	/**
	 * Deletes suppliers from the displayed list
	 * 
	 * @param suppliers
	 */
	public void deleteData(List<SupplierDTO> suppliers);

	/**
	 * Set loader for paging
	 * 
	 * @param loader
	 */
	public void setPagingLoader(
			PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SupplierDTO>> remoteLoader);

	/**
	 * Get create supplier window
	 * 
	 * @return
	 */
	public CreateSupplierView getCreateView();

	/**
	 * Get edit supplier window
	 * 
	 * @return
	 */
	public EditSupplierView getEditSupplierView();
	
	/**
	 * Gets name filter value
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
