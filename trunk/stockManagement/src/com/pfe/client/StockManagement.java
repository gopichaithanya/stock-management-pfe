package com.pfe.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.pfe.client.mvp.AppActivityMapper;
import com.pfe.client.mvp.AppPlaceHistoryMapper;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.ClientFactoryImpl;
import com.pfe.client.mvp.places.ProductTypesPlace;
import com.sencha.gxt.widget.core.client.ContentPanel;

public class StockManagement implements EntryPoint {
	

	@Override
	@SuppressWarnings("deprecation")
	public void onModuleLoad() {
		
		System.out.println("test");
		
		ClientFactory clientFactory = new ClientFactoryImpl();
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper,
				eventBus);
		
		//This is the panel that will contain the different views
		ContentPanel mainPanel = new ContentPanel();
		activityManager.setDisplay(mainPanel);

		//TODO remove this --> it's only a test
		ProductTypesPlace testPlace = new ProductTypesPlace();
		
		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		// register controller with place history handler
		historyHandler.register(placeController, eventBus, testPlace);
		// handle whatever place arrives when application starts
		historyHandler.handleCurrentHistory();
		
		RootPanel.get().add(mainPanel);
	}

}
