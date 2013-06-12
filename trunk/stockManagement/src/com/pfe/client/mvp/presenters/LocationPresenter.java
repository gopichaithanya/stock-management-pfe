package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;

/**
 * Controls the views displaying location related information
 * 
 * @author Alexandra
 *
 */
public interface LocationPresenter extends Presenter {

	/**
	 * Goes to a new place
	 * 
	 * @param place
	 */
	public void goTo(Place place);
	
	/**
	 * Retrieves location by id
	 * 
	 * @param id
	 */
	public void find(Long id);
	
}
