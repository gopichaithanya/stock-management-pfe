package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.shared.dto.LocationDTO;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;

public interface LocationListView extends IsWidget {
	
	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(LocationPresenter presenter);
	
	/**
	 * Sets data to be rendered
	 * 
	 * @param locations
	 */
	public void setData(List<LocationDTO> locations);
	
	/**
	 * Gets the view data
	 * 
	 * @return locations displayed
	 */
	public ListStore<LocationDTO> getData();
	
	/**
	 * Adds new line in list
	 * 
	 * @param location
	 */
	public void addData(LocationDTO location);
	
	/**
	 * Removes records from list
	 * 
	 * @param locations
	 */
	public void deleteData(List<LocationDTO> locations);
	
	/**
	 * Set loader for paging
	 * 
	 * @param loader
	 */
	public void setPagingLoader(
			PagingLoader<FilterPagingLoadConfig, PagingLoadResult<LocationDTO>> remoteLoader);
	
	/**
	 * Refresh the grid view
	 * 
	 */
	public void refreshGrid();
	
	/**
	 * Get edit location window
	 * 
	 * @return the edit window
	 */
	public EditLocationView getEditView();
	
	/**
	 * Get create location window
	 * 
	 * @return
	 */
	public CreateLocationView getCreateView();

	/**
	 * Get stock window
	 * 
	 * @return
	 */
	public StockActionsView getStockView();
}
