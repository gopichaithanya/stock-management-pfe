package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;

/**
 * Controls ProductTypesView
 * 
 * @author Alexandra
 * 
 */
public interface ProductTypesPresenter extends Presenter {

	/**
	 * Goes to a new place
	 * 
	 * @param place
	 */
	public void goTo(Place place);
}
