package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.presenters.Presenter;
import com.pfe.client.mvp.views.WelcomeView;

/**
 * Controls WelcomeView 
 * 
 * @author Alexandra
 *
 */
public class WelcomeActivity extends AbstractActivity implements Presenter {

	private ClientFactory clientFactory;
	private WelcomeView welcomeView;

	public WelcomeActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void bind() {
		welcomeView.setPresenter(this);

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		welcomeView = clientFactory.getWelcomeView();
		bind();
		
		panel.setWidget(welcomeView.asWidget());
		
	}

}
