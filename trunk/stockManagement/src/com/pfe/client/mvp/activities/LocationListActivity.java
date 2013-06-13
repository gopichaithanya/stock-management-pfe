package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.client.mvp.views.LocationListView;
import com.pfe.client.service.LocationServiceAsync;
import com.pfe.shared.dto.LocationDTO;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;

public class LocationListActivity extends AbstractActivity implements
		LocationPresenter {
	private ClientFactory clientFactory;
	private LocationServiceAsync locationService;
	
	private LocationListView view;
	
	public LocationListActivity(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
		this.locationService = clientFactory.getLocationService();
	}
	
	@Override
	public void bind() {
		view.setPresenter(this);

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getLocationListView();
		//view.maskGrid();
		bind();
		loadPages();
		//view.unmaskGrid();
		panel.setWidget(view.asWidget());

	}
	
	/**
	 * Sets paging parameters and loads list pages
	 */
	private void loadPages() {
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<LocationDTO>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<LocationDTO>>() {

			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<LocationDTO>> callback) {
				locationService.search(loadConfig, callback);

			}

		};
		final PagingLoader<FilterPagingLoadConfig, PagingLoadResult<LocationDTO>> remoteLoader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<LocationDTO>>(
				proxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};
		remoteLoader.setRemoteSort(true);
		remoteLoader
				.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, LocationDTO, PagingLoadResult<LocationDTO>>(
						view.getData()));

		view.setPagingLoader(remoteLoader);
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);

	}

	@Override
	public void find(Long id) {
		locationService.find(id, new AsyncCallback<LocationDTO>() {
			
			@Override
			public void onSuccess(LocationDTO result) {
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
