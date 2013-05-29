package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.model.Supplier;

public interface SupplierServiceAsync {

	void getAll(AsyncCallback<List<Supplier>> callback);

}
