package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.presenters.LocationPresenter;

public interface LocationListView extends IsWidget {
	
	/**
	 * Sets the presenter of this view
	 * 
	 * @param presenter
	 */
	public void setPresenter(LocationPresenter presenter);

}
