package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.ProductTypeDTO;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface ProductTypeServiceAsync {
	
	void create(ProductTypeDTO productType, AsyncCallback<ProductTypeDTO> callback);

	void update(ProductTypeDTO updatedBuffer, AsyncCallback<ProductTypeDTO> callback);

	void delete(ProductTypeDTO productType, AsyncCallback<Void> callback);

	void search(FilterPagingLoadConfig config, AsyncCallback<PagingLoadResult<ProductTypeDTO>> callback);

	void getAll(AsyncCallback<List<ProductTypeDTO>> callback);

	void find(Long id, AsyncCallback<ProductTypeDTO> callback);

	void delete(List<ProductTypeDTO> productTypes, AsyncCallback<Void> callback);

}
