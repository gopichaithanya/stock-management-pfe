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

}
