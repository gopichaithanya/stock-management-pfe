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
	 * Set loader for paging
	 * 
	 * @param loader
	 */
	public void setPagingLoader(
			PagingLoader<FilterPagingLoadConfig, PagingLoadResult<LocationDTO>> remoteLoader);

}
