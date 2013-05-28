package com.pfe.client.mvp;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.pfe.client.mvp.views.ProductTypeDetailView;
import com.pfe.client.mvp.views.ProductTypeDetailViewImpl;
import com.pfe.client.mvp.views.ProductTypeListView;
import com.pfe.client.mvp.views.ProductTypeListViewImpl;
import com.pfe.client.service.ProductTypeService;
import com.pfe.client.service.ProductTypeServiceAsync;

public class ClientFactoryImpl implements ClientFactory {

	private static EventBus eventBus;
	private static PlaceController placeController;

	private static ProductTypeServiceAsync productTypeService;

	private static ProductTypeListView productTypesView;
	private static ProductTypeDetailView productTypeView;

	@Override
	public EventBus getEventBus() {
		if (eventBus == null)
			eventBus = new SimpleEventBus();
		return eventBus;
	}

	
	@Override
	@SuppressWarnings("deprecation")
	public PlaceController getPlaceController() {
		if (placeController == null)
			placeController = new PlaceController(eventBus);
		return placeController;
	}

	@Override
	public ProductTypeServiceAsync getProductTypeService() {
		if (productTypeService == null) {
			productTypeService = GWT.create(ProductTypeService.class);
		}
		return productTypeService;
	}

	@Override
	public ProductTypeListView getProductTypesView() {
		if (productTypesView == null) {
			productTypesView = new ProductTypeListViewImpl();
		}
		return productTypesView;
	}

	@Override
	public ProductTypeDetailView getProductTypeView() {
		if (productTypeView == null) {
			productTypeView = new ProductTypeDetailViewImpl();
		}
		return productTypeView;
	}

}
