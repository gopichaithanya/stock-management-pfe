package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.LocationDetailPlace;
import com.pfe.client.mvp.views.LocationDetailView;
import com.pfe.client.service.LocationServiceAsync;
import com.pfe.shared.dto.LocationDTO;

public class LocationDetailActivity extends AbstractActivity {
	
	private ClientFactory clientFactory;
	private LocationServiceAsync rpcService;
	private LocationDetailView view;
	private String id;

	public LocationDetailActivity(ClientFactory clientFactory, LocationDetailPlace place){
		this.clientFactory = clientFactory;
		this.rpcService  =clientFactory.getLocationService();
		this.id = place.getId();
	}
	
	
	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getLocationDetailView();
		Long locationId = Long.parseLong(id);

		rpcService.find(locationId, new AsyncCallback<LocationDTO>() {
			
			@Override
			public void onSuccess(LocationDTO result) {
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
