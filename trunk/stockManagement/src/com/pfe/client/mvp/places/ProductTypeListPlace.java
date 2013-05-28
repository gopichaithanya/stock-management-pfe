package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * URL for ProductTypeListView. This Place has no specific token.
 * 
 * @author Alexandra
 * 
 */
public class ProductTypeListPlace extends Place {

	/**
	 * Translates the ProductTypeListPlace POJO to the corresponding URL and vice
	 * versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<ProductTypeListPlace> {

		/**
		 * Creates the place
		 */
		public ProductTypeListPlace getPlace(String token) {
			return new ProductTypeListPlace();
		}

		public String getToken(ProductTypeListPlace place) {
			return null;
		}
	}
}
