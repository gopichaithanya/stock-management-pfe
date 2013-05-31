package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.ProductTypeDetailPlace;
import com.pfe.client.mvp.views.ProductTypeDetailView;

public class ProductTypeDetailActivity extends AbstractActivity {

	private ClientFactory clientFactory;
	private ProductTypeDetailView view;
	private String token;
	
	public ProductTypeDetailActivity(ClientFactory clientFactory, ProductTypeDetailPlace place){
		this.clientFactory = clientFactory;
		this.token = place.getDetails();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getProductTypeDetailView();
		view.setData(token);
		panel.setWidget(view.asWidget());

	}

}
