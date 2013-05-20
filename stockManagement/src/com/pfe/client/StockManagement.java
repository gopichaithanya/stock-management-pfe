package com.pfe.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.client.service.ProductTypeService;
import com.pfe.client.service.ProductTypeServiceAsync;
import com.pfe.shared.model.ProductType;

public class StockManagement implements EntryPoint {

	@Override
	public void onModuleLoad() {
		System.out.println("test");
		ProductTypeServiceAsync rpcService = GWT.create(ProductTypeService.class);
		rpcService.getProductTypes(new AsyncCallback<List<ProductType>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("error");
				
			}

			@Override
			public void onSuccess(List<ProductType> result) {
				
				if(result != null){
					for(ProductType pt:result){
						System.out.println(pt.getDescription());
					}
				}
			}
		});
	}

}
