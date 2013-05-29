package com.pfe.client.mvp;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.pfe.client.mvp.views.ProductTypeDetailView;
import com.pfe.client.mvp.views.ProductTypeDetailViewImpl;
import com.pfe.client.mvp.views.ProductTypeListView;
import com.pfe.client.mvp.views.ProductTypeListViewImpl;
import com.pfe.client.mvp.views.SupplierListView;
import com.pfe.client.mvp.views.SupplierListViewImpl;
import com.pfe.client.mvp.views.WelcomeView;
import com.pfe.client.mvp.views.WelcomeViewImpl;
import com.pfe.client.service.ProductTypeService;
import com.pfe.client.service.ProductTypeServiceAsync;
import com.pfe.client.service.SupplierService;
import com.pfe.client.service.SupplierServiceAsync;

public class ClientFactoryImpl implements ClientFactory {

	private static EventBus eventBus;
	private static PlaceController placeController;

	private static ProductTypeServiceAsync productTypeService;
	private static SupplierServiceAsync supplierService;

	private static WelcomeView welcomeView;
	private static ProductTypeListView productTypeListView;
	private static ProductTypeDetailView productTypeDetailView;
	private static SupplierListView supplierListView;
	

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
	public SupplierServiceAsync getSupplierService() {
		if (supplierService == null) {
			supplierService = GWT.create(SupplierService.class);
		}
		return supplierService;
	}

	@Override
	public ProductTypeListView getProductTypeListView() {
		if (productTypeListView == null) {
			productTypeListView = new ProductTypeListViewImpl();
		}
		return productTypeListView;
	}

	@Override
	public ProductTypeDetailView getProductTypeDetailView() {
		if (productTypeDetailView == null) {
			productTypeDetailView = new ProductTypeDetailViewImpl();
		}
		return productTypeDetailView;
	}


	@Override
	public WelcomeView getWelcomeView() {
		if (welcomeView == null) {
			welcomeView = new WelcomeViewImpl();
		}
		return welcomeView;
	}


	@Override
	public SupplierListView getSupplierListView() {
		if (supplierListView == null) {
			supplierListView = new SupplierListViewImpl();
		}
		return supplierListView;
	}

}
