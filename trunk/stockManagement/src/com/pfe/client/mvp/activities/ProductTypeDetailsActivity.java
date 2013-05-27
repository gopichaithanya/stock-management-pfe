package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.ProductTypeDetailsPlace;
import com.pfe.client.mvp.views.ProductTypeView;

public class ProductTypeDetailsActivity extends AbstractActivity {

	private ClientFactory clientFactory;
	private ProductTypeView pTypeView;
	private String token;
	
	public ProductTypeDetailsActivity(ClientFactory clientFactory, ProductTypeDetailsPlace place){
		this.clientFactory = clientFactory;
		this.token = place.getTypeDetails();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		pTypeView = clientFactory.getProductTypeView();
		pTypeView.setData(token);
		panel.setWidget(pTypeView.asWidget());

	}

}
