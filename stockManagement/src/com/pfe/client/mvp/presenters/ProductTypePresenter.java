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
	public void create(ProductType productType);

	/**
	 * Updates product type
	 * 
	 * @param initial
	 * @param updatedBuffer
	 */
	public void update(ProductType initial, ProductType updatedBuffer);

	/**
	 * Deletes type
	 * 
	 * @param productType
	 */
	public void delete(ProductType productType);

	/**
	 * Filters list by name. Creates new paging load configuration corresponding
	 * to the filtered data
	 * 
	 * @param name
	 */
	public void filter(String name);

	/**
	 * Clears filters. Loads pages without filters
	 * 
	 */
	public void clearFilter();

	/**
	 * Loads selected type data in details panel
	 * 
	 * @param productType
	 */
	public void displayDetailsView(ProductType productType);

}
