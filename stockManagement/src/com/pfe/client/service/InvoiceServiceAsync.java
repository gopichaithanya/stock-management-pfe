package com.pfe.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.InvoiceDTO;

public interface InvoiceServiceAsync {

	void update(InvoiceDTO updatedInvoice, AsyncCallback<InvoiceDTO> callback);

}
