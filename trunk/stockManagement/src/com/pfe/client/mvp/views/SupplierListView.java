package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.SupplierPresenter;

/**
 * Contains UI components to display a list of suppliers
 * 
 * @author Alexandra
 *
 */
public interface SupplierListView extends IsWidget {

	
	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(SupplierPresenter presenter);
}
