package com.pfe.client.mvp.activities;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.LocationDetailPlace;
import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.client.mvp.views.LocationListView;
import com.pfe.client.service.LocationServiceAsync;
import com.pfe.client.service.StockServiceAsync;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.StockDTO;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

public class LocationListActivity extends AbstractActivity implements LocationPresenter {
	private ClientFactory clientFactory;
	private LocationServiceAsync locationService;
	private StockServiceAsync stockService;
	
	private LocationListView view;
	
	public LocationListActivity(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
		this.locationService = clientFactory.getLocationService();
		this.stockService = clientFactory.getStockService();
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

	@Override
	public void sell(final StockDTO fromStock, int quantity) {
		stockService.sell(fromStock, quantity, new AsyncCallback<StockDTO>() {
			
			@Override
			public void onSuccess(StockDTO result) {
				if(result == null){
					view.getEditView().deleteData(fromStock);
					
				} else{
					view.getEditView().updateData(result);
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof BusinessException) {
					BusinessException exp = (BusinessException) caught;
					AlertMessageBox alertBox = new AlertMessageBox("Error", exp.getMessage());
					alertBox.show();
				}
				
			}
		});
		
	}

	@Override
	public void getAll() {
		locationService.getAll(new AsyncCallback<List<LocationDTO>>() {
			
			@Override
			public void onSuccess(List<LocationDTO> result) {
				view.getEditView().setLocations(result);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void ship(final StockDTO fromStock, int quantity, LocationDTO toLocation) {
		stockService.ship(fromStock, quantity, toLocation, new AsyncCallback<StockDTO>() {
			
			@Override
			public void onSuccess(StockDTO result) {
				if(result == null){
					view.getEditView().deleteData(fromStock);
				} else{
					view.getEditView().updateData(result);
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public void displayDetailsView(LocationDTO location) {
		String token = location.getId().toString();
		goTo(new LocationDetailPlace(token));
		
	}

}
