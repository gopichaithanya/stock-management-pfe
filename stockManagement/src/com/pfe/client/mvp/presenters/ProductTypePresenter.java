package com.pfe.client.mvp.presenters;

import com.google.gwt.place.shared.Place;
import com.pfe.shared.model.ProductType;

/**
 * Controls ProductTypesView
 * 
 * @author Alexandra
 * 
 */
public interface ProductTypePresenter extends Presenter {

	/**
	 * Goes to a new place
	 * 
	 * @param place
	 */
	public void goTo(Place place);
	
	/**
	 * Calls RPC service to create a new product type
	 * 
	 * @param productType
	 */
	public void createProductType(ProductType productType);
	
	/**
	 * Updates product type
	 * 
	 * @param initial
	 * @param updatedBuffer
	 */
	public void updateProductType(ProductType initial, ProductType updatedBuffer);
	
	/**
	 * Deletes type
	 * 
	 * @param productType
	 */
	public void deleteProductType(ProductType productType);
}
