package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.InvoiceDetailPlace;
import com.pfe.client.mvp.views.InvoiceDetailView;
import com.pfe.client.service.InvoiceServiceAsync;
import com.pfe.shared.dto.InvoiceDTO;

public class InvoiceDetailActivity extends AbstractActivity {
	
	private ClientFactory clientFactory;
	private InvoiceServiceAsync rpcService;
	private InvoiceDetailView view;
	private String id;

	public InvoiceDetailActivity(ClientFactory clientFactory, InvoiceDetailPlace place){
		this.clientFactory = clientFactory;
		this.rpcService  =clientFactory.getInvoiceService();
		this.id = place.getId();
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getInvoiceDetailView();
		Long invoiceId = Long.parseLong(id);

		rpcService.find(invoiceId, new AsyncCallback<InvoiceDTO>() {
			
			@Override
			public void onSuccess(InvoiceDTO result) {
				view.setData(result);
				panel.setWidget(view.asWidget());
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
