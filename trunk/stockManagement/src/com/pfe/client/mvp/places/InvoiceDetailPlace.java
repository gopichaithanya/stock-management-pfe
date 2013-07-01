package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * URL for InvoiceDetail Panel. The specific token for this place is the invoice ID
 * 
 * @author Alexandra
 * 
 */
public class InvoiceDetailPlace extends Place {

	private String id;
	
	public String getId() {
		return id;
	}

	public InvoiceDetailPlace(String token){
		this.id = token;
	}
	
	/**
	 * Translates the InvoiceDetail Panel to the corresponding URL and vice
	 * versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<InvoiceDetailPlace> {

		/**
		 * Creates the place
		 */
		public InvoiceDetailPlace getPlace(String token) {
			return new InvoiceDetailPlace(token);
		}

		public String getToken(InvoiceDetailPlace place) {
			return place.getId();
		}
	}
}
