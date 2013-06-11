package com.pfe.client.mvp.views;

import java.util.List;

import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.SupplierDTO;

/**
 * Contains UI components for supplier name and description
 * 
 * @author Alexandra
 *
 */
public interface CreateInvoiceView {

	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(InvoicePresenter presenter);
	
	/**
	 * Sets available product types for shipments
	 * 
	 * @param types
	 */
	public void setProductTypes(List<ProductTypeDTO> types);
	
	/**
	 * Sets available suppliers to choose from combo box
	 * 
	 * @param suppliers
	 */
	public void setSuppliers(List<SupplierDTO> suppliers);
	
	/**
	 * Clears the view data
	 */
	public void clearData();
	
	/**
	 * Shows window
	 */
	public void show();

	/**
	 * Hides window
	 */
	public void hide();
}
