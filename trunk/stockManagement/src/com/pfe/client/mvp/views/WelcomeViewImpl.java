package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.Presenter;

public class WelcomeViewImpl implements WelcomeView {
	
	private SimplePanel panel;

	public WelcomeViewImpl(){
		panel = new SimplePanel();
		panel.add(new Label("This is the welcome view"));
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
