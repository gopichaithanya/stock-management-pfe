package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface ProductTypeServiceAsync {
	
	void create(ProductType productType,
			AsyncCallback<ProductType> callback);

	void update(ProductType initial, ProductType updatedBuffer,
			AsyncCallback<ProductType> callback);

	void delete(ProductType productType, AsyncCallback<Void> callback);

	void search(FilterPagingLoadConfig config,
			AsyncCallback<PagingLoadResult<ProductType>> callback);

	void filter(FilterPagingLoadConfig config, String name,
			AsyncCallback<PagingLoadResult<ProductType>> callback);

	void getAll(AsyncCallback<List<ProductTypeDTO>> callback);

}
