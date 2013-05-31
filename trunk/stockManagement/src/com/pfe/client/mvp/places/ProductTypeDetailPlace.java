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
public class ProductTypeDetailPlace extends Place {
	
	private String details;
	 
	public String getDetails() {
		return details;
	}


	public ProductTypeDetailPlace(String token){
		this.details = token;
	}
	

	/**
	 * Translates the ProductTypeDetails Panel to the corresponding URL and vice
	 * versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<ProductTypeDetailPlace> {

		/**
		 * Creates the place
		 */
		public ProductTypeDetailPlace getPlace(String token) {
			return new ProductTypeDetailPlace(token);
		}

		public String getToken(ProductTypeDetailPlace place) {
			return place.getDetails();
		}
	}
}