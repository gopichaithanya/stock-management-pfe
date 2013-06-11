package com.pfe.client.mvp.activities;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.client.mvp.views.InvoiceListView;
import com.pfe.client.service.InvoiceServiceAsync;
import com.pfe.client.service.ProductTypeServiceAsync;
import com.pfe.client.service.SupplierServiceAsync;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;

public class InvoiceListActivity extends AbstractActivity implements
		InvoicePresenter {
	
	private ClientFactory clientFactory;
	private InvoiceServiceAsync invoiceService;
	private SupplierServiceAsync supplierService;
	private ProductTypeServiceAsync pTypeService;
	
	private InvoiceListView view;

	public InvoiceListActivity(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
		invoiceService = clientFactory.getInvoiceService();
		supplierService = clientFactory.getSupplierService();
		pTypeService = clientFactory.getProductTypeService();
	}

	@Override
	public void bind() {
		view.setPresenter(this);

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getInvoiceListView();
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
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<InvoiceDTO>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<InvoiceDTO>>() {

			@Override
			public void load(FilterPagingLoadConfig loadConfig,
					AsyncCallback<PagingLoadResult<InvoiceDTO>> callback) {
				invoiceService.search(loadConfig, callback);

			}

		};
		final PagingLoader<FilterPagingLoadConfig, PagingLoadResult<InvoiceDTO>> remoteLoader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<InvoiceDTO>>(
				proxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};
		remoteLoader.setRemoteSort(true);
		remoteLoader
				.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, InvoiceDTO, PagingLoadResult<InvoiceDTO>>(
						view.getData()));

		view.setPagingLoader(remoteLoader);
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);

	}

	@Override
	public void create(InvoiceDTO invoice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void find(Long id) {
		invoiceService.find(id, new AsyncCallback<InvoiceDTO>() {
			
			@Override
			public void onSuccess(InvoiceDTO result) {
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
	public void update(InvoiceDTO updatedInvoice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(InvoiceDTO invoice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getProductTypes() {
		pTypeService.getAll(new AsyncCallback<List<ProductTypeDTO>>() {
			
			@Override
			public void onSuccess(List<ProductTypeDTO> result) {
				view.getEditView().setProductTypes(result);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public void getSuppliers() {
		supplierService.getAll(new AsyncCallback<List<SupplierDTO>>() {
			
			@Override
			public void onSuccess(List<SupplierDTO> result) {
				view.getEditView().setSuppliers(result);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
