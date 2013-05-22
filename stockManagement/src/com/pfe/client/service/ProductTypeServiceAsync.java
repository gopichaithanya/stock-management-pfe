package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.model.ProductType;

public interface ProductTypeServiceAsync {

	void getProductTypes(AsyncCallback<List<ProductType>> callback);

	void createProductType(ProductType productType,
			AsyncCallback<ProductType> callback);

}
