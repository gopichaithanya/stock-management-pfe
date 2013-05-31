package com.pfe.client.mvp.activities;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.client.mvp.views.SupplierListView;
import com.pfe.client.service.SupplierServiceAsync;
import com.pfe.shared.dto.SupplierDto;
import com.pfe.shared.model.Supplier;

public class SupplierListActivity extends AbstractActivity implements
		SupplierPresenter {

	private ClientFactory clientFactory;
	private SupplierServiceAsync rpcService;
	private SupplierListView view;
	
	public SupplierListActivity(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
		this.rpcService = clientFactory.getSupplierService();
	}
	
	@Override
	public void bind() {
		view.setPresenter(this);

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		
		rpcService.getAll(new AsyncCallback<List<SupplierDto>>() {
			
			@Override
			public void onSuccess(List<SupplierDto> result) {
				List<SupplierDto> l = result;

			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	@Override
	public void create(Supplier supplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Supplier initial, Supplier updatedBuffer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Supplier supplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void filter(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearFilter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayDetailsView(Supplier supplier) {
		// TODO Auto-generated method stub

	}

}
