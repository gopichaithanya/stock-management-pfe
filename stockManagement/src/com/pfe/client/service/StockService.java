package com.pfe.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.StockDTO;

/**
 * RPC Service that handles operations on stocks
 * 
 * @author Alexandra
 * 
 */
@RemoteServiceRelativePath("gxt3/stockService")
public interface StockService extends RemoteService {

	/**
	 * Sells products from stock
	 * 
	 * @param stock
	 * @param quantity
	 * @return stock with updated quantity or null if total quantity sold
	 */
	public StockDTO sell(StockDTO stock, int quantity) throws BusinessException;

	/**
	 * Ships products from given stock to another location
	 * 
	 * @param stock
	 * @param quantity
	 * @param destination
	 * @return stock with updated quantity or null if total quantity moved
	 * @throws BusinessException
	 */
	public StockDTO ship(StockDTO stock, int quantity, LocationDTO destination)
			throws BusinessException;
}
