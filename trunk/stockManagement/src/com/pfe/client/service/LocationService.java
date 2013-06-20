package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
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
	 * Retrieves location by id
	 * 
	 * @param id
	 * @return location
	 */
	public LocationDTO find(Long id);
	
	/**
	 * Retrieves all locations from database
	 * 
	 * @return
	 */
	public List<LocationDTO> getAll();
	
	/**
	 * Retrieves locations with paging
	 * 
	 * @param config
	 * @return
	 */
	public PagingLoadResult<LocationDTO> search(FilterPagingLoadConfig config);
	
	/**
	 * Adds new location in database
	 * 
	 * @param location
	 * @return
	 * @throws BusinessException
	 */
	public LocationDTO create(LocationDTO location) throws BusinessException;
}
