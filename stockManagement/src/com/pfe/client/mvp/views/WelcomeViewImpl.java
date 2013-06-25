package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.Presenter;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class WelcomeViewImpl implements WelcomeView {
	
	private VerticalLayoutContainer panel;

	public WelcomeViewImpl(){
		panel = new VerticalLayoutContainer();
		VerticalLayoutData data = new VerticalLayoutData();
		data.setMargins(new Margins(10, 20, 10, 20));
		panel.add(new Label("ERP module for stock management."), data);
		panel.add(new Label("Version : 1.0."), data);
		panel.add(new Label("Author : Alexandra"), data);
		panel.add(new Label("Use the toolbar buttons to navigate through the application."), data);
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		
	}

}
