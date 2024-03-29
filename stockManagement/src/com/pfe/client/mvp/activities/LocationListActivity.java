package com.pfe.client.mvp.activities;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
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
import com.pfe.client.service.LocationTypeServiceAsync;
import com.pfe.client.service.StockServiceAsync;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.LocationTypeDTO;
import com.pfe.shared.dto.StockDTO;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterConfigBean;
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
	private LocationTypeServiceAsync locationTypeService;
	
	private LocationListView view;
	
	public LocationListActivity(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
		this.locationService = clientFactory.getLocationService();
		this.stockService = clientFactory.getStockService();
		this.locationTypeService = clientFactory.getLocationTypeService();
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
		search();
		//view.unmaskGrid();
		panel.setWidget(view.asWidget());

	}
	
	@Override
	public void search() {
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<LocationDTO>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<LocationDTO>>() {

			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<LocationDTO>> callback) {
				
				List<FilterConfig> filters = new ArrayList<FilterConfig>();
				FilterConfigBean nameFilter = new FilterConfigBean();
				nameFilter.setField("nameFilter");
				nameFilter.setType("String");
				nameFilter.setValue(view.getFilterValue());
				filters.add(nameFilter);
				loadConfig.setFilters(filters);
				
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
				view.getStockView().setData(result);
				view.getStockView().show();
				Log.info("log example!");
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
					view.getStockView().deleteData(fromStock);
					
				} else{
					view.getStockView().updateData(result);
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
				view.getStockView().setLocations(result);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void create(LocationDTO location) {
		locationService.create(location, new AsyncCallback<LocationDTO>() {
			
			@Override
			public void onSuccess(LocationDTO result) {
				view.addData(result);
				view.refreshGrid();
				view.getCreateView().hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	@Override
	public void update(LocationDTO location) {
		locationService.update(location, new AsyncCallback<LocationDTO>() {
			
			@Override
			public void onSuccess(LocationDTO result) {
				view.getEditView().hide();
				view.updateData(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}


	@Override
	public void delete(final List<LocationDTO> locations) {
		locationService.delete(locations, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				view.deleteData(locations);
				view.refreshGrid();
				
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
	public void ship(final StockDTO fromStock, int quantity, LocationDTO toLocation) {
		stockService.ship(fromStock, quantity, toLocation, new AsyncCallback<StockDTO>() {
			
			@Override
			public void onSuccess(StockDTO result) {
				if(result == null){
					view.getStockView().deleteData(fromStock);
				} else{
					view.getStockView().updateData(result);
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

	@Override
	public void getLocationTypes(final String window) {
		locationTypeService.getAll(new AsyncCallback<List<LocationTypeDTO>>() {
			
			@Override
			public void onSuccess(List<LocationTypeDTO> result) {
				if("create".equals(window)){
					view.getCreateView().setLocationTypes(result);
				} else if("edit".equals(window)){
					view.getEditView().setLocationTypes(result);
				}
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public void searchStocks(String productTypeName, LocationDTO location) {
		stockService.search(productTypeName, location, new AsyncCallback<List<StockDTO>>() {
			
			@Override
			public void onSuccess(List<StockDTO> result) {
				view.getStockView().setStocks(result);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
