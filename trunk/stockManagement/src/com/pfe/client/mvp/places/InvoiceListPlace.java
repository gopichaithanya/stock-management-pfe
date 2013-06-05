package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * URL for InvoiceListView. This Place has no specific token.
 * 
 * @author Alexandra
 * 
 */
public class InvoiceListPlace extends Place {

	/**
	 * Translates InvoiceListPlace POJO to corresponding URL and vice versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<InvoiceListPlace> {

		/**
		 * Creates the place
		 */
		public InvoiceListPlace getPlace(String token) {
			return new InvoiceListPlace();
		}

		public String getToken(InvoiceListPlace place) {
			return null;
		}
	}
}
