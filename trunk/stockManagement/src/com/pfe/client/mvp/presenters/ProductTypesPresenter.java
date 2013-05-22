package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;
import com.pfe.shared.model.ProductType;

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
	
	/**
	 * Call RPC service to create a new product type
	 * 
	 * @param productType
	 * @return
	 */
	public ProductType addProductType(ProductType productType);
}
