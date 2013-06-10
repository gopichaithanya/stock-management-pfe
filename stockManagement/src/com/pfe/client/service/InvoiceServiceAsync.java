package com.pfe.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.InvoiceDTO;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface InvoiceServiceAsync {

	void update(InvoiceDTO updatedInvoice, AsyncCallback<InvoiceDTO> callback);

	void search(FilterPagingLoadConfig config,
			AsyncCallback<PagingLoadResult<InvoiceDTO>> callback);

}
