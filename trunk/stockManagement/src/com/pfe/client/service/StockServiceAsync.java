package com.pfe.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.StockDTO;

public interface StockServiceAsync {

	void sell(StockDTO stock, int quantity, AsyncCallback<StockDTO> callback);

}
