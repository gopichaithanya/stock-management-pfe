package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.StockDTO;

public interface StockServiceAsync {

	void sell(StockDTO stock, int quantity, AsyncCallback<StockDTO> callback);

	void ship(StockDTO stock, int quantity, LocationDTO destination,
			AsyncCallback<StockDTO> callback);

	void find(ProductTypeDTO productType, AsyncCallback<List<StockDTO>> callback);

	void search(String productTypeName, LocationDTO location, AsyncCallback<List<StockDTO>> callback);

}
