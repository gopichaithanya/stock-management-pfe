package com.pfe.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.pfe.client.mvp.activities.ProductTypeDetailActivity;
import com.pfe.client.mvp.places.ProductTypeDetailPlace;

/**
 * Maps places to activities, i.e. tells what activity to start for each place.
 * Controls the eastern details panel
 * 
 * @author Alexandra
 *
 */
public class DetailsActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public DetailsActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	public Activity getActivity(Place place) {
		if (place instanceof ProductTypeDetailPlace) {
			return new ProductTypeDetailActivity(clientFactory,
					(ProductTypeDetailPlace) place);
		} else
			return null;
	}

}
