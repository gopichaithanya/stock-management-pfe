package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * URL for LocationListView. This Place has no specific token.
 * 
 * @author Alexandra
 *
 */
public class LocationListPlace extends Place {

	/**
	 * Translates LocationListPlace POJO to corresponding URL and vice versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<LocationListPlace> {

		/**
		 * Creates the place
		 */
		public LocationListPlace getPlace(String token) {
			return new LocationListPlace();
		}

		public String getToken(LocationListPlace place) {
			return null;
		}
	}
}
