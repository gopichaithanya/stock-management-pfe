package com.pfe.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.BusinessException;
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
	 * @return stock with updated quantity
	 */
	public StockDTO sell(StockDTO stock, int quantity) throws BusinessException;
}
