package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.InvoiceDTO;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface InvoiceServiceAsync {

	void update(InvoiceDTO updatedInvoice, AsyncCallback<InvoiceDTO> callback);

	void search(FilterPagingLoadConfig config,
			AsyncCallback<PagingLoadResult<InvoiceDTO>> callback);

	void find(Long id, AsyncCallback<InvoiceDTO> callback);

	void create(InvoiceDTO invoice, AsyncCallback<InvoiceDTO> callback);

	void delete(InvoiceDTO invoice, AsyncCallback<Void> callback);

	void delete(List<InvoiceDTO> invoices, AsyncCallback<Void> callback);

}
