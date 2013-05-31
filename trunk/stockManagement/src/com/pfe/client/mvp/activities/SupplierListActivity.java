package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.SupplierDetailPlace;
import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.client.mvp.views.SupplierListView;
import com.pfe.client.service.SupplierServiceAsync;
import com.pfe.shared.dto.SupplierDto;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;

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
		view = clientFactory.getSupplierListView();
		view.maskGrid();
		bind();
		loadPages();
		view.unmaskGrid();
		panel.setWidget(view.asWidget());

	}
	
	/**
	 * Sets paging parameters and loads list pages
	 */
	private void loadPages() {
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<SupplierDto>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<SupplierDto>>() {

			@Override
			public void load(FilterPagingLoadConfig loadConfig,
					AsyncCallback<PagingLoadResult<SupplierDto>> callback) {
				rpcService.search(loadConfig, callback);

			}

		};
		final PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SupplierDto>> remoteLoader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SupplierDto>>(
				proxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};
		remoteLoader.setRemoteSort(true);
		remoteLoader
				.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, SupplierDto, PagingLoadResult<SupplierDto>>(
						view.getData()));

		view.setPagingLoader(remoteLoader);
	}

	@Override
	public void create(SupplierDto supplier) {
		rpcService.create(supplier, new AsyncCallback<SupplierDto>() {
			
			@Override
			public void onSuccess(SupplierDto result) {
				view.addData(result);
				view.getCreateView().hide();
				view.maskGrid();
				view.refreshGrid();
				view.unmaskGrid();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	@Override
	public void update(SupplierDto initial, SupplierDto updatedBuffer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(SupplierDto supplier) {
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
	public void displayDetailsView(SupplierDto supplier) {
		String token = supplier.getId().toString();
		goTo(new SupplierDetailPlace(token));

	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
		
	}

	@Override
	public void find(Long id) {
		rpcService.find(id, new AsyncCallback<SupplierDto>() {
			
			@Override
			public void onSuccess(SupplierDto result) {
				view.getEditView().setData(result);
				view.getEditView().show();
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
