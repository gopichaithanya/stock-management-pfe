package com.pfe.client.mvp;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.pfe.client.mvp.views.InvoiceDetailView;
import com.pfe.client.mvp.views.InvoiceDetailViewImpl;
import com.pfe.client.mvp.views.InvoiceListView;
import com.pfe.client.mvp.views.InvoiceListViewImpl;
import com.pfe.client.mvp.views.LocationDetailView;
import com.pfe.client.mvp.views.LocationDetailViewImpl;
import com.pfe.client.mvp.views.LocationListView;
import com.pfe.client.mvp.views.LocationListViewImpl;
import com.pfe.client.mvp.views.ProductTypeDetailView;
import com.pfe.client.mvp.views.ProductTypeDetailViewImpl;
import com.pfe.client.mvp.views.ProductTypeListView;
import com.pfe.client.mvp.views.ProductTypeListViewImpl;
import com.pfe.client.mvp.views.ReportsView;
import com.pfe.client.mvp.views.ReportsViewImpl;
import com.pfe.client.mvp.views.SupplierDetailView;
import com.pfe.client.mvp.views.SupplierDetailViewImpl;
import com.pfe.client.mvp.views.SupplierListView;
import com.pfe.client.mvp.views.SupplierListViewImpl;
import com.pfe.client.mvp.views.WelcomeView;
import com.pfe.client.mvp.views.WelcomeViewImpl;
import com.pfe.client.service.InvoiceService;
import com.pfe.client.service.InvoiceServiceAsync;
import com.pfe.client.service.LocationService;
import com.pfe.client.service.LocationServiceAsync;
import com.pfe.client.service.LocationTypeService;
import com.pfe.client.service.LocationTypeServiceAsync;
import com.pfe.client.service.ProductTypeService;
import com.pfe.client.service.ProductTypeServiceAsync;
import com.pfe.client.service.ShipmentService;
import com.pfe.client.service.ShipmentServiceAsync;
import com.pfe.client.service.StockService;
import com.pfe.client.service.StockServiceAsync;
import com.pfe.client.service.SupplierService;
import com.pfe.client.service.SupplierServiceAsync;

public class ClientFactoryImpl implements ClientFactory {

	private static EventBus eventBus;
	private static PlaceController placeController;

	private static ProductTypeServiceAsync productTypeService;
	private static SupplierServiceAsync supplierService;
	private static InvoiceServiceAsync invoiceService;
	private static ShipmentServiceAsync shipmentService;
	private static LocationServiceAsync locationService;
	private static StockServiceAsync stockService;
	private static LocationTypeServiceAsync locationTypeService;

//	private static WelcomeView welcomeView;
//	
//	private static ProductTypeListView productTypeListView;
//	private static ProductTypeDetailView productTypeDetailView;
//	private static SupplierListView supplierListView;
//	private static SupplierDetailView supplierDetailView; 
//	private static InvoiceListView invoiceListView;
//	private static LocationListView locationListView;

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
	public InvoiceServiceAsync getInvoiceService() {
		if (invoiceService == null) {
			invoiceService = GWT.create(InvoiceService.class);
		}
		return invoiceService;
	}
	

	@Override
	public ShipmentServiceAsync getShipmentService() {
		if (shipmentService == null) {
			shipmentService = GWT.create(ShipmentService.class);
		}
		return shipmentService;
	}
	
	@Override
	public LocationServiceAsync getLocationService() {
		if (locationService == null) {
			locationService = GWT.create(LocationService.class);
		}
		return locationService;
	}
	

	@Override
	public StockServiceAsync getStockService() {
		if (stockService == null) {
			stockService = GWT.create(StockService.class);
		}
		return stockService;
	}
	
	@Override
	public LocationTypeServiceAsync getLocationTypeService() {
		if (locationTypeService == null) {
			locationTypeService = GWT.create(LocationTypeService.class);
		}
		return locationTypeService;
	}
	
	@Override
	public ProductTypeListView getProductTypeListView() {
//		if (productTypeListView == null) {
//			productTypeListView = new ProductTypeListViewImpl();
//		}
		return new ProductTypeListViewImpl();
	}

	@Override
	public ProductTypeDetailView getProductTypeDetailView() {
//		if (productTypeDetailView == null) {
//			productTypeDetailView = new ProductTypeDetailViewImpl();
//		}
		return new ProductTypeDetailViewImpl();
	}


	@Override
	public WelcomeView getWelcomeView() {
//		if (welcomeView == null) {
//			welcomeView = new WelcomeViewImpl();
//		}
		return new WelcomeViewImpl();
	}


	@Override
	public SupplierListView getSupplierListView() {
//		if (supplierListView == null) {
//			supplierListView = new SupplierListViewImpl();
//		}
		return new SupplierListViewImpl();
	}


	@Override
	public SupplierDetailView getSupplierDetailView() {
//		if (supplierDetailView == null) {
//			supplierDetailView = new SupplierDetailViewImpl();
//		}
		return new SupplierDetailViewImpl();
	}


	@Override
	public InvoiceListView getInvoiceListView() {
//		if (invoiceListView == null) {
//			invoiceListView = new InvoiceListViewImpl();
//		}
		return new InvoiceListViewImpl();
	}


	@Override
	public LocationListView getLocationListView() {
//		if (locationListView == null) {
//			locationListView = new LocationListViewImpl();
//		}
		return new LocationListViewImpl();
	}


	@Override
	public LocationDetailView getLocationDetailView() {
		return new LocationDetailViewImpl();
	}


	@Override
	public ReportsView getReportsView() {
		return new ReportsViewImpl();
	}


	@Override
	public InvoiceDetailView getInvoiceDetailView() {
		return new InvoiceDetailViewImpl();
	}

}
