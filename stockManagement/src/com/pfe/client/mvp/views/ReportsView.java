package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.ReportsPresenter;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.StockDTO;

/**
 * Displays reports on stocks 
 * 
 * @author Alexandra
 *
 */
public interface ReportsView extends IsWidget {
 
	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(ReportsPresenter presenter);
	
	/**
	 * Adds available products in combo box
	 * 
	 * @param productTypes
	 */
	public void setProductTypes(List<ProductTypeDTO> productTypes);
	
	/**
	 * Sets stocks found after search on product type
	 * 
	 * @param stocks
	 */
	public void setStocks(List<StockDTO> stocks);
}
