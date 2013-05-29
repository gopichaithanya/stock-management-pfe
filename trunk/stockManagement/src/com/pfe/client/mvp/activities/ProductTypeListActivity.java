package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.ProductTypeDetailsPlace;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.client.mvp.views.ProductTypeListView;
import com.pfe.client.service.ProductTypeServiceAsync;
import com.pfe.shared.BusinessException;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

public class ProductTypeListActivity extends AbstractActivity implements
		ProductTypePresenter {

	private ClientFactory clientFactory;
	private ProductTypeServiceAsync rpcService;
	private ProductTypeListView pTypesView;

	public ProductTypeListActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.rpcService = clientFactory.getProductTypeService();
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {

		pTypesView = clientFactory.getProductTypesView();
		pTypesView.maskGrid();
		bind();
		loadPages();
		pTypesView.unmaskGrid();
		panel.setWidget(pTypesView.asWidget());

	}

	/**
	 * Sets paging parameters and loads list pages
	 */
	private void loadPages() {
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ProductType>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ProductType>>() {

			@Override
			public void load(FilterPagingLoadConfig loadConfig,
					AsyncCallback<PagingLoadResult<ProductType>> callback) {
				rpcService.search(loadConfig, callback);

			}

		};
		final PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductType>> remoteLoader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductType>>(
				proxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};
		remoteLoader.setRemoteSort(true);
		remoteLoader
				.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, ProductType, PagingLoadResult<ProductType>>(
						pTypesView.getData()));

		pTypesView.setPagingLoader(remoteLoader);
	}

	@Override
	public void create(ProductType productType) {

		rpcService.create(productType, new AsyncCallback<ProductType>() {

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof BusinessException) {
					BusinessException exp = (BusinessException) caught;
					AlertMessageBox alertBox = new AlertMessageBox("Error", exp.getMessage());
					alertBox.show();
				}
			}

			@Override
			public void onSuccess(ProductType result) {
				pTypesView.addData(result);
				pTypesView.getCreateWindow().hide();
				pTypesView.refreshGrid();
			}
		});
	}

	@Override
	public void update(ProductType initial, ProductType updatedBuffer) {
		rpcService.update(initial, updatedBuffer,
				new AsyncCallback<ProductType>() {

					@Override
					public void onSuccess(ProductType result) {
						pTypesView.updateData(result);
						pTypesView.getEditWindow().hide();
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
	public void delete(final ProductType productType) {
		rpcService.delete(productType, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				pTypesView.deleteData(productType);
				pTypesView.refreshGrid();
				pTypesView.unmaskGrid();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void filter(final String name) {

		pTypesView = clientFactory.getProductTypesView();
		pTypesView.maskGrid();

		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ProductType>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ProductType>>() {

			@Override
			public void load(FilterPagingLoadConfig loadConfig,
					AsyncCallback<PagingLoadResult<ProductType>> callback) {
				rpcService.filter(loadConfig, name, callback);

			}

		};
		final PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductType>> remoteLoader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductType>>(
				proxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};
		remoteLoader.setRemoteSort(true);
		remoteLoader
				.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, ProductType, PagingLoadResult<ProductType>>(
						pTypesView.getData()));

		pTypesView.setPagingLoader(remoteLoader);
		pTypesView.refreshGrid();
		pTypesView.unmaskGrid();
	}

	@Override
	public void clearFilter() {

		pTypesView = clientFactory.getProductTypesView();
		pTypesView.maskGrid();
		loadPages();
		pTypesView.refreshGrid();
		pTypesView.unmaskGrid();
	}

	@Override
	public void displayDetailsView(ProductType productType) {
		String token = productType.getName() + " \t\n\r\f"
				+ productType.getDescription();
		goTo(new ProductTypeDetailsPlace(token));

	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);

	}

	@Override
	public void bind() {
		pTypesView.setPresenter(this);

	}

}
