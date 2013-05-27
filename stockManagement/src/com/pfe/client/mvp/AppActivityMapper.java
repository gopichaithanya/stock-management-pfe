package com.pfe.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.pfe.client.mvp.activities.ProductTypeActivity;
import com.pfe.client.mvp.places.ProductTypeDetailsPlace;
import com.pfe.client.mvp.places.ProductTypesPlace;

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
	private ProductTypeActivity productTypeActivity;

	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	public Activity getActivity(Place place) {
		if(place instanceof ProductTypesPlace){
			productTypeActivity = new ProductTypeActivity(clientFactory);
			return productTypeActivity;
		} else if(place instanceof ProductTypeDetailsPlace){
			return productTypeActivity;
		} else
			return null;
	}
}
