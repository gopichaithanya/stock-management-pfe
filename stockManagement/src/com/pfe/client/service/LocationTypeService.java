package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.dto.LocationTypeDTO;

/**
 * RPC Service for operations in location type
 * 
 * @author Alexandra
 * 
 */
@RemoteServiceRelativePath("gxt3/locationTypeService")
public interface LocationTypeService extends RemoteService {
	
	/**
	 * Retrieves all location types from database
	 * 
	 * @return
	 */
	public List<LocationTypeDTO> getAll();

}
