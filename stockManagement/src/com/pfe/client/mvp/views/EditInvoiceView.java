package com.pfe.client.mvp.views;

import com.pfe.shared.dto.InvoiceDTO;

/**
 * Contains UI components that display invoice information : code, supplier, shipments, payment type,
 * creation date, debt
 * 
 * @author Alexandra
 *
 */
public interface EditInvoiceView {

	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	//public void setPresenter(SupplierPresenter presenter);
	
	/**
	 * Sets the view data
	 * 
	 * @param invoice
	 */
	public void setData(InvoiceDTO invoice);
	
	/**
	 * Clears the UI componentss
	 * 
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
