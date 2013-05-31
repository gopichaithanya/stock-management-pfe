package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * URL for ProductTypeDetails Panel. The specific token of this place contains
 * the type details, name and description
 * 
 * @author Alexandra
 * 
 */
public class ProductTypeDetailsPlace extends Place {
	
	private String details;
	 
	public String getDetails() {
		return details;
	}


	public ProductTypeDetailsPlace(String token){
		this.details = token;
	}
	

	/**
	 * Translates the ProductTypeDetails Panel to the corresponding URL and vice
	 * versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<ProductTypeDetailsPlace> {

		/**
		 * Creates the place
		 */
		public ProductTypeDetailsPlace getPlace(String token) {
			return new ProductTypeDetailsPlace(token);
		}

		public String getToken(ProductTypeDetailsPlace place) {
			return place.getDetails();
		}
	}
}