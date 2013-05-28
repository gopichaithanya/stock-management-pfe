package com.pfe.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class WelcomePlace extends Place {

	/**
	 * Translates the WelcomePlace POJO to the corresponding URL and vice versa.
	 * 
	 * @author Alexandra
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<WelcomePlace> {

		/**
		 * Creates the place
		 */
		public WelcomePlace getPlace(String token) {
			return new WelcomePlace();
		}

		public String getToken(WelcomePlace place) {
			return null;
		}
	}

}
