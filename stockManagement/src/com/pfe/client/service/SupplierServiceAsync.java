package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.SupplierDto;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface SupplierServiceAsync {

	void getAll(AsyncCallback<List<SupplierDto>> callback);

	void search(FilterPagingLoadConfig config,
			AsyncCallback<PagingLoadResult<SupplierDto>> callback);

	void find(Long id, AsyncCallback<SupplierDto> callback);

	void create(SupplierDto supplier, AsyncCallback<SupplierDto> callback);

	void update(SupplierDto initial, SupplierDto buffer,
			AsyncCallback<SupplierDto> callback);

	

}
