package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.Presenter;

/**
 * Contains UI components that display welcome message
 * 
 * @author Alexandra
 *
 */
public interface WelcomeView extends IsWidget {

	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(Presenter presenter);
	
}
