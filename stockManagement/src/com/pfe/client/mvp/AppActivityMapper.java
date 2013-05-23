package com.pfe.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.pfe.client.mvp.activities.ProductTypeActivity;
import com.pfe.client.mvp.places.ProductTypesPlace;

/**
 * Maps places to activities, i.e. tells what activity to start for each place
 * 
 * @author Alexandra
 * 
 */
public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	public Activity getActivity(Place place) {
		if(place instanceof ProductTypesPlace){
			return new ProductTypeActivity(clientFactory);
		} else
			return null;
	}
}
