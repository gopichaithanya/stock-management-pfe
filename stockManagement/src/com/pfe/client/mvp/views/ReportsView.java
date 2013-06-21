package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.ReportsPresenter;

/**
 * Displays reports on stocks 
 * 
 * @author Alexandra
 *
 */
public interface ReportsView extends IsWidget {

	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(ReportsPresenter presenter);
}
