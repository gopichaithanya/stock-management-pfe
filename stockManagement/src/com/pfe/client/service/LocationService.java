package com.pfe.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.dto.LocationDTO;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * RPC Service for operations on location
 * 
 * @author Alexandra
 *
 */
@RemoteServiceRelativePath("gxt3/locationService")
public interface LocationService extends RemoteService {

	
	/**
	 * Retrieves locations with paging
	 * 
	 * @param config
	 * @return
	 */
	public PagingLoadResult<LocationDTO> search(FilterPagingLoadConfig config);
}
