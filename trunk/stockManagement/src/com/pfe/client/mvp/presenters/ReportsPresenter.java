package com.pfe.client.mvp.presenters;

import com.pfe.shared.dto.ProductTypeDTO;

/**
 * Controls the report view
 * 
 * @author Alexandra
 *
 */
public interface ReportsPresenter extends Presenter {

	/**
	 * Calls service to retrieve stocks of given type from all the locations,
	 * including the warehouse.
	 * 
	 * @param productType
	 */
	public void getStocks(ProductTypeDTO productType);
}
