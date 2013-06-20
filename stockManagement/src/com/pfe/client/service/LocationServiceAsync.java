package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.LocationDTO;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface LocationServiceAsync {

	void search(FilterPagingLoadConfig config,
			AsyncCallback<PagingLoadResult<LocationDTO>> callback);

	void find(Long id, AsyncCallback<LocationDTO> callback);

	void getAll(AsyncCallback<List<LocationDTO>> callback);

	void create(LocationDTO location, AsyncCallback<LocationDTO> callback);

	void delete(LocationDTO location, AsyncCallback<Void> callback);

	void update(LocationDTO updatedLocation, AsyncCallback<LocationDTO> callback);

	void delete(List<LocationDTO> locations, AsyncCallback<Void> callback);

}
