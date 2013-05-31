package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.SupplierDetailPlace;
import com.pfe.client.mvp.views.SupplierDetailView;

public class SupplierDetailActivity extends AbstractActivity {
	
	private ClientFactory clientFactory;
	private SupplierDetailView view;
	private String id;

	public SupplierDetailActivity(ClientFactory clientFactory, SupplierDetailPlace place){
		this.clientFactory = clientFactory;
		this.id = place.getId();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub

	}

}
