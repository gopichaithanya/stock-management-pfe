package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.dto.SupplierDto;

/**
 * RPC Service that handles supplier CRUD
 * 
 * @author Alexandra
 *
 */
@RemoteServiceRelativePath("gxt3/supplierService")
public interface SupplierService extends RemoteService {
	
	/**
	 * Retrieves all suppliers from database
	 * 
	 * @return
	 */
	public List<SupplierDto> getAll();

}
