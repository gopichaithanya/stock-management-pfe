package com.pfe.client.mvp.activities;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.ProductTypeDetailPlace;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.client.mvp.views.ProductTypeListView;
import com.pfe.client.service.ProductTypeServiceAsync;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.ProductTypeDTO;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterConfigBean;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

public class ProductTypeListActivity extends AbstractActivity implements ProductTypePresenter {

	private ClientFactory clientFactory;
	private ProductTypeServiceAsync rpcService;
	private ProductTypeListView view;

	public ProductTypeListActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.rpcService = clientFactory.getProductTypeService();
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getProductTypeListView();
		view.maskGrid();
		bind();
		search();
		view.unmaskGrid();
		panel.setWidget(view.asWidget());

	}

	@Override
	public void search() {
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ProductTypeDTO>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ProductTypeDTO>>() {

			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<ProductTypeDTO>> callback) {
				
				List<FilterConfig> filters = new ArrayList<FilterConfig>();
				FilterConfigBean nameFilter = new FilterConfigBean();
				nameFilter.setField("nameFilter");
				nameFilter.setType("String");
				nameFilter.setValue(view.getFilterValue());
				filters.add(nameFilter);
				loadConfig.setFilters(filters);
				rpcService.search(loadConfig, callback);
			}

		};
		final PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductTypeDTO>> remoteLoader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductTypeDTO>>(
				proxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};
		remoteLoader.setRemoteSort(true);
		remoteLoader
				.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, ProductTypeDTO, PagingLoadResult<ProductTypeDTO>>(
						view.getData()));

		view.setPagingLoader(remoteLoader);
	}
	
	@Override
	public void find(Long id) {
		rpcService.find(id, new AsyncCallback<ProductTypeDTO>() {
			
			@Override
			public void onSuccess(ProductTypeDTO result) {
				view.getEditView().setData(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public void create(ProductTypeDTO productType) {

		rpcService.create(productType, new AsyncCallback<ProductTypeDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof BusinessException) {
					BusinessException exp = (BusinessException) caught;
					AlertMessageBox alertBox = new AlertMessageBox("Error", exp.getMessage());
					alertBox.show();
				}
			}

			@Override
			public void onSuccess(ProductTypeDTO result) {
				view.addData(result);
				view.getCreateView().hide();
				view.refreshGrid();
			}
		});
	}

	@Override
	public void update(final ProductTypeDTO updatedType) {
		rpcService.update(updatedType, new AsyncCallback<ProductTypeDTO>() {

					@Override
					public void onSuccess(ProductTypeDTO result) {
						view.updateData(result);
						view.getEditView().hide();
					}

					@Override
					public void onFailure(Throwable caught) {
						//Reload initial data
						find(updatedType.getId());
						if (caught instanceof BusinessException) {
							BusinessException exp = (BusinessException) caught;
							AlertMessageBox alertBox = new AlertMessageBox("Error", exp.getMessage());
							alertBox.show();
						}

					}
				});
	}

	@Override
	public void delete(final List<ProductTypeDTO> productTypes) {
		rpcService.delete(productTypes, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				view.deleteData(productTypes);
				view.refreshGrid();
				view.unmaskGrid();
			}

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof BusinessException) {
					BusinessException exp = (BusinessException) caught;
					AlertMessageBox alertBox = new AlertMessageBox("Error", exp.getMessage());
					alertBox.show();
				}
				view.unmaskGrid();
			}
		});

	}

	@Override
	public void displayDetailsView(ProductTypeDTO productType) {
		String token = productType.getName() + " \t\n\r\f" + productType.getDescription();
		goTo(new ProductTypeDetailPlace(token));

	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);

	}

	@Override
	public void bind() {
		view.setPresenter(this);

	}

}
