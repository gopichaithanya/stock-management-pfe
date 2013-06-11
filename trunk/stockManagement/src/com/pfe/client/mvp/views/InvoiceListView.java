package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.shared.dto.InvoiceDTO;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;

/**
 * Contains UI components to display a list of invoice
 * 
 * @author Alexandra
 * 
 */
public interface InvoiceListView extends IsWidget {

	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(InvoicePresenter presenter);

	/**
	 * Sets data to be rendered
	 * 
	 * @param invoices
	 */
	public void setData(List<InvoiceDTO> invoices);

	/**
	 * Gets the view data
	 * 
	 * @return
	 */
	public ListStore<InvoiceDTO> getData();

	/**
	 * Clears the view components
	 */
	public void clearData();

	/**
	 * Adds new line in list
	 * 
	 * @param invoice
	 */
	public void addData(InvoiceDTO invoice);

	/**
	 * Updates record in list
	 * 
	 * @param invoice
	 */
	public void updateData(InvoiceDTO invoice);

	/**
	 * Deletes record from list
	 * 
	 * @param invoice
	 */
	public void deleteData(InvoiceDTO invioce);

	/**
	 * Set loader for paging
	 * 
	 * @param loader
	 */
	public void setPagingLoader(
			PagingLoader<FilterPagingLoadConfig, PagingLoadResult<InvoiceDTO>> remoteLoader);

	/**
	 * 
	 * @return
	 */
	// public CreateInvoiceView getCreateView();

	/**
	 * 
	 * @return
	 */
	public EditInvoiceView getEditView();

	/**
	 * Refresh the grid view
	 * 
	 */
	public void refreshGrid();

	/**
	 * Masks invoice list while data is loading
	 * 
	 */
	public void maskGrid();

	public void unmaskGrid();

}
