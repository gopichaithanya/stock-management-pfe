package com.pfe.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.pfe.client.mvp.activities.InvoiceListActivity;
import com.pfe.client.mvp.activities.LocationListActivity;
import com.pfe.client.mvp.activities.ProductTypeListActivity;
import com.pfe.client.mvp.activities.SupplierListActivity;
import com.pfe.client.mvp.activities.WelcomeActivity;
import com.pfe.client.mvp.places.InvoiceListPlace;
import com.pfe.client.mvp.places.LocationListPlace;
import com.pfe.client.mvp.places.ProductTypeDetailPlace;
import com.pfe.client.mvp.places.ProductTypeListPlace;
import com.pfe.client.mvp.places.SupplierDetailPlace;
import com.pfe.client.mvp.places.SupplierListPlace;
import com.pfe.client.mvp.places.WelcomePlace;

/**
 * Maps places to activities, i.e. tells what activity to start for each place.
 * Controls the central panel
 * 
 * @author Alexandra
 * 
 */
public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;
	
	//ACTIVITIES
	private ProductTypeListActivity productTypeActivity;
	private SupplierListActivity supplierActivity;
	private InvoiceListActivity invoiceActivity;
	private LocationListActivity locationActivity;

	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	public Activity getActivity(Place place) {
		if(place instanceof WelcomePlace){
			return new WelcomeActivity(clientFactory);
			
		} else if(place instanceof ProductTypeListPlace){
			productTypeActivity = new ProductTypeListActivity(clientFactory);
			return productTypeActivity;
			
		} else if(place instanceof SupplierListPlace){
			supplierActivity = new SupplierListActivity(clientFactory);
			return supplierActivity;
			
		} else if(place instanceof InvoiceListPlace){
			invoiceActivity = new InvoiceListActivity(clientFactory);
			return invoiceActivity;
			
		} else if(place instanceof LocationListPlace){
			locationActivity = new LocationListActivity(clientFactory);
			return locationActivity;
			
		} else if(place instanceof ProductTypeDetailPlace){
			return productTypeActivity;
			
		} else if(place instanceof SupplierDetailPlace){
			return supplierActivity;
			
		} else
			return null;
	}
}
