package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface ProductTypeServiceAsync {

	void getAll(AsyncCallback<List<ProductType>> callback);

	void create(ProductType productType,
			AsyncCallback<ProductType> callback);

	void update(ProductType initial, ProductType updatedBuffer,
			AsyncCallback<ProductType> callback);

	void delete(ProductType productType, AsyncCallback<Void> callback);

	void search(PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<ProductType>> callback);

}
