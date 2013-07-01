package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.shared.dto.InvoiceDTO;

/**
 * This view displays detailed information on selected invoice.
 * 
 * @author Alexandra
 *
 */
public interface InvoiceDetailView extends IsWidget {
	
	/**
	 * Clears the view data
	 */
	public void clearData();

	/**
	 * Sets data to display
	 * 
	 * @param invoice
	 */
	public void setData(InvoiceDTO invoice);

}
