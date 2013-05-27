package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * URL for ProductTypeDetails Panel. he specific token for this place is a ProductType id
 * 
 * @author Alexandra
 * 
 */
public class ProductTypeDetailsPlace extends Place {
	
	private String typeDetails;
	 
	public String getTypeDetails() {
		return typeDetails;
	}


	public ProductTypeDetailsPlace(String token){
		this.typeDetails = token;
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
			return null;
		}
	}
}