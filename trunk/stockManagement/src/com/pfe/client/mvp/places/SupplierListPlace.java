package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * URL for SupplierListView. This Place has no specific token.
 * 
 * @author Alexandra
 *
 */
public class SupplierListPlace extends Place {
	
	/**
	 * Translates the SupplierListPlace POJO to the corresponding URL and vice
	 * versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<SupplierListPlace> {

		/**
		 * Creates the place
		 */
		public SupplierListPlace getPlace(String token) {
			return new SupplierListPlace();
		}

		public String getToken(SupplierListPlace place) {
			return null;
		}
	}

}
