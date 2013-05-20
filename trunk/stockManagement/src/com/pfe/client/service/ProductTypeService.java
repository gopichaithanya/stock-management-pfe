package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pfe.shared.model.ProductType;

@RemoteServiceRelativePath("gxt3/productTypeService")
public interface ProductTypeService extends RemoteService {

	List<ProductType> getProductTypes();
}
