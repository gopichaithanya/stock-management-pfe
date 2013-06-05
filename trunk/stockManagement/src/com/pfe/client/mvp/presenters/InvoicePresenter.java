package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;


/**
 * Controls the views displaying invoice related information
 * 
 * @author Alexandra
 */
public interface InvoicePresenter extends Presenter {
	
	/**
	 * Goes to a new place
	 * 
	 * @param place
	 */
	public void goTo(Place place);
	
	
}
