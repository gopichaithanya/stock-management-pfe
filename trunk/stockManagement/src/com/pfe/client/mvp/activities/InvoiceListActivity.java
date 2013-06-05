package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.client.mvp.views.InvoiceListView;
import com.pfe.client.service.InvoiceServiceAsync;

public class InvoiceListActivity extends AbstractActivity implements
		InvoicePresenter {
	
	private ClientFactory clientFactory;
	private InvoiceServiceAsync invoiceService;
	
	private InvoiceListView view;

	public InvoiceListActivity(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
		invoiceService = clientFactory.getInvoiceService();
	}

	@Override
	public void bind() {
		view.setPresenter(this);

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub

	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);

	}

}
