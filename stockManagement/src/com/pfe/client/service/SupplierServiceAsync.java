package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface SupplierServiceAsync {

	void getAll(AsyncCallback<List<SupplierDTO>> callback);

	void search(FilterPagingLoadConfig config,
			AsyncCallback<PagingLoadResult<SupplierDTO>> callback);

	void find(Long id, AsyncCallback<SupplierDTO> callback);

	void create(SupplierDTO supplier, AsyncCallback<SupplierDTO> callback);

	void update(SupplierDTO updatedSupplier, AsyncCallback<SupplierDTO> callback);

	void delete(SupplierDTO supplier, AsyncCallback<Void> callback);


}
