package com.pfe.client.mvp.views;

import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.SupplierDTO;

/**
 * Contains UI components that display supplier information : name, description, invoices, debt
 * 
 * @author Alexandra
 *
 */

public interface EditSupplierView {

	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(SupplierPresenter presenter);
	
	/**
	 * Sets the view data
	 * 
	 * @param supplier
	 */
	public void setData(SupplierDTO supplier);
	
	/**
	 * Gets the view data
	 * 
	 * @param supplier
	 */
	public SupplierDTO gettData();
	
	/**
	 * Deletes invoice
	 * 
	 * @param invoice
	 */
	public void removeInvoice(InvoiceDTO invoice);
	
	public void updateInvoice(InvoiceDTO invoice);
	
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
	
	/**
	 * Get editInvoice window
	 * 
	 * @return
	 */
	public EditInvoiceView getEditInvoiceView();
	
}
