package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


/**
 * URL for SupplierDetails Panel. The specific token for this place is the supplier id
 * 
 * @author Alexandra
 * 
 */
public class SupplierDetailPlace extends Place {

	private String id;
	
	public String getId() {
		return id;
	}

	public SupplierDetailPlace(String token){
		this.id = token;
	}
	
	/**
	 * Translates the SupplierDetails Panel to the corresponding URL and vice
	 * versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<SupplierDetailPlace> {

		/**
		 * Creates the place
		 */
		public SupplierDetailPlace getPlace(String token) {
			return new SupplierDetailPlace(token);
		}

		public String getToken(SupplierDetailPlace place) {
			return place.getId();
		}
	}
	
}
