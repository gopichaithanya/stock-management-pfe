package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface ProductTypeServiceAsync {

	void getProductTypes(AsyncCallback<List<ProductType>> callback);

	void createProductType(ProductType productType,
			AsyncCallback<ProductType> callback);

	void updateProductType(ProductType initial, ProductType updatedBuffer,
			AsyncCallback<ProductType> callback);

	void deleteProductType(ProductType productType, AsyncCallback<Void> callback);

	void getTypesWithPaging(PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<ProductType>> callback);

}
