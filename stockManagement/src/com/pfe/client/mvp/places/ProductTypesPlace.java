package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * URL for ProductTypeListView. This Place has no specific token.
 * 
 * @author Alexandra
 * 
 */
public class ProductTypesPlace extends Place {

	/**
	 * Translates the ProductTypeListPlace POJO to the corresponding URL and vice
	 * versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<ProductTypesPlace> {

		/**
		 * Creates the place
		 */
		public ProductTypesPlace getPlace(String token) {
			return new ProductTypesPlace();
		}

		public String getToken(ProductTypesPlace place) {
			return null;
		}
	}
}
