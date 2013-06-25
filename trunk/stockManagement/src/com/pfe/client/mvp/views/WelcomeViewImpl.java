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
		VerticalLayoutData data1 = new VerticalLayoutData();
		VerticalLayoutData data2 = new VerticalLayoutData();
		data1.setMargins(new Margins(10, 20, 10, 20));
		data2.setMargins(new Margins(30, 20, 10, 20));
		panel.add(new Label("ERP module for stock management."), data2);
		panel.add(new Label("Version : 1.0."), data1);
		panel.add(new Label("Author : Alexandra"), data1);
		panel.add(new Label("Use the toolbar buttons to navigate through the application."), data1);
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
