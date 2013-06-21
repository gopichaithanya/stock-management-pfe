package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ReportsPlace extends Place {

	/**
	 * Translates the PeportsPlace POJO to the corresponding URL and vice versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<ReportsPlace> {

		/**
		 * Creates the place
		 */
		public ReportsPlace getPlace(String token) {
			return new ReportsPlace();
		}

		public String getToken(ReportsPlace place) {
			return null;
		}
	}
}
