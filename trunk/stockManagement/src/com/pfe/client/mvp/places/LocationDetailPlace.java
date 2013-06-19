package com.pfe.client.mvp.places;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * URL for LocationDetail Panel. The specific token for this place is the location id
 * 
 * @author Alexandra
 * 
 */
public class LocationDetailPlace extends Place {

	private String id;
	
	public String getId() {
		return id;
	}

	public LocationDetailPlace(String token){
		this.id = token;
	}
	
	/**
	 * Translates the LocationDetail Panel to the corresponding URL and vice
	 * versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<LocationDetailPlace> {

		/**
		 * Creates the place
		 */
		public LocationDetailPlace getPlace(String token) {
			return new LocationDetailPlace(token);
		}

		public String getToken(LocationDetailPlace place) {
			return place.getId();
		}
	}
}
