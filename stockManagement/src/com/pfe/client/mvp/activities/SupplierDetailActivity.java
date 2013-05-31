package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.SupplierDetailPlace;
import com.pfe.client.mvp.views.SupplierDetailView;
import com.pfe.client.service.SupplierServiceAsync;
import com.pfe.shared.dto.SupplierDto;

public class SupplierDetailActivity extends AbstractActivity {
	
	private ClientFactory clientFactory;
	private SupplierServiceAsync rpcService;
	private SupplierDetailView view;
	private String id;

	public SupplierDetailActivity(ClientFactory clientFactory, SupplierDetailPlace place){
		this.clientFactory = clientFactory;
		this.rpcService  =clientFactory.getSupplierService();
		this.id = place.getId();
	}
	
	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		
		view = clientFactory.getSupplierDetailView();
		Long supplierId = Long.parseLong(id);
		rpcService.find(supplierId, new AsyncCallback<SupplierDto>() {
			
			@Override
			public void onSuccess(SupplierDto result) {
				view.setData(result);
				panel.setWidget(view.asWidget());
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
