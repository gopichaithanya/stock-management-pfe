package com.pfe.client.mvp.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.client.mvp.views.ProductTypesView;
import com.pfe.client.service.ProductTypeServiceAsync;
import com.pfe.shared.BusinessException;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.box.MessageBox;

public class ProductTypeActivity extends AbstractActivity implements
		ProductTypePresenter {

	private ClientFactory clientFactory;
	private ProductTypeServiceAsync rpcService;
	private ProductTypesView pTypesView;

	public ProductTypeActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.rpcService = clientFactory.getProductTypeService();
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		
		pTypesView = clientFactory.getProductTypesView();
		bind();

		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ProductType>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ProductType>>() {

			@Override
			public void load(FilterPagingLoadConfig loadConfig,
					AsyncCallback<PagingLoadResult<ProductType>> callback) {
				rpcService.getTypesWithPaging(loadConfig, callback);

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
	  
	    pTypesView.getLayout().setLoader(remoteLoader);
	    pTypesView.getLayout().bindPagingToolBar();
		panel.setWidget(pTypesView.asWidget());
		
//		rpcService.getProductTypes(new AsyncCallback<List<ProductType>>() {
//
//			@Override
//			public void onSuccess(List<ProductType> result) {
//				pTypesView.setData(result);
//				panel.setWidget(pTypesView.asWidget());
//
//			}
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO do something here
//
//			}
//		});
	}

	@Override
	public void bind() {
		pTypesView.setPresenter(this);

	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createProductType(ProductType productType) {

		rpcService.createProductType(productType,
				new AsyncCallback<ProductType>() {

					@Override
					public void onFailure(Throwable caught) {
						if (caught instanceof BusinessException) {
							BusinessException exp = (BusinessException) caught;
							MessageBox messageBox = new MessageBox(exp
									.getMessage());
							messageBox.show();
						}
					}

					@Override
					public void onSuccess(ProductType result) {
						pTypesView.addData(result);
						pTypesView.getCreateWindow().hide();
						pTypesView.getLayout().refreshGrid();
					}
				});
	}

	@Override
	public void updateProductType(ProductType initial, ProductType updatedBuffer) {
		rpcService.updateProductType(initial, updatedBuffer,
				new AsyncCallback<ProductType>() {

					@Override
					public void onSuccess(ProductType result) {
						pTypesView.updateData(result);
						pTypesView.getEditWindow().hide();
					}

					@Override
					public void onFailure(Throwable caught) {
						if (caught instanceof BusinessException) {
							// List<ProductType> l = pTypesView.getData();
							BusinessException exp = (BusinessException) caught;
							MessageBox messageBox = new MessageBox(exp
									.getMessage());
							messageBox.show();
						}

					}
				});

	}

	@Override
	public void deleteProductType(final ProductType productType) {
		rpcService.deleteProductType(productType, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				pTypesView.deleteData(productType);
				pTypesView.getLayout().refreshGrid();
				pTypesView.getLayout().unmaskGrid();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}
}
