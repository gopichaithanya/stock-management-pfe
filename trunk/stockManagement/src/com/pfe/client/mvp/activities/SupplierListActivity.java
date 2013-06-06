package com.pfe.client.mvp.activities;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.places.SupplierDetailPlace;
import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.client.mvp.views.SupplierListView;
import com.pfe.client.service.InvoiceServiceAsync;
import com.pfe.client.service.ProductTypeServiceAsync;
import com.pfe.client.service.SupplierServiceAsync;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

public class SupplierListActivity extends AbstractActivity implements
		SupplierPresenter {

	private ClientFactory clientFactory;
	private SupplierServiceAsync supplierService;
	private InvoiceServiceAsync invoiceService;
	private ProductTypeServiceAsync productTypeService;
	
	private SupplierListView view;

	public SupplierListActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		supplierService = clientFactory.getSupplierService();
		invoiceService = clientFactory.getInvoiceService();
		productTypeService = clientFactory.getProductTypeService();
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
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<SupplierDTO>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<SupplierDTO>>() {

			@Override
			public void load(FilterPagingLoadConfig loadConfig,
					AsyncCallback<PagingLoadResult<SupplierDTO>> callback) {
				supplierService.search(loadConfig, callback);

			}

		};
		final PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SupplierDTO>> remoteLoader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SupplierDTO>>(
				proxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};
		remoteLoader.setRemoteSort(true);
		remoteLoader
				.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, SupplierDTO, PagingLoadResult<SupplierDTO>>(
						view.getData()));

		view.setPagingLoader(remoteLoader);
	}

	@Override
	public void create(SupplierDTO supplier) {
		supplierService.create(supplier, new AsyncCallback<SupplierDTO>() {

			@Override
			public void onSuccess(SupplierDTO result) {
				view.addData(result);
				view.getCreateView().hide();
				view.maskGrid();
				view.refreshGrid();
				view.unmaskGrid();
			}

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof BusinessException){
					view.unmaskGrid();
					BusinessException exp = (BusinessException) caught;
					AlertMessageBox alertBox = new AlertMessageBox("Error", exp.getMessage());
					alertBox.show();
				}
			}
		});

	}

	@Override
	public void update(SupplierDTO initial, SupplierDTO updatedBuffer) {
		supplierService.update(initial, updatedBuffer,
				new AsyncCallback<SupplierDTO>() {

					@Override
					public void onSuccess(SupplierDTO result) {
						view.getEditSupplierView().hide();
						view.updateData(result);

					}

					@Override
					public void onFailure(Throwable caught) {
						if (caught instanceof BusinessException){
							BusinessException exp = (BusinessException) caught;
							AlertMessageBox alertBox = new AlertMessageBox("Error", exp.getMessage());
							alertBox.show();
						}
					}
				});

	}

	@Override
	public void delete(final SupplierDTO supplier) {
		supplierService.delete(supplier, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				view.deleteData(supplier);
				view.refreshGrid();
				view.unmaskGrid();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof BusinessException){
					view.unmaskGrid();
					BusinessException exp = (BusinessException) caught;
					AlertMessageBox alertBox = new AlertMessageBox("Error", exp.getMessage());
					alertBox.show();
				}
			}
		});

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
	public void displayDetailsView(SupplierDTO supplier) {
		String token = supplier.getId().toString();
		goTo(new SupplierDetailPlace(token));

	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);

	}

	@Override
	public void find(Long id) {
		supplierService.find(id, new AsyncCallback<SupplierDTO>() {

			@Override
			public void onSuccess(SupplierDTO result) {
				view.getEditSupplierView().setData(result);
				view.getEditSupplierView().show();

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void updateInvoice(final InvoiceDTO initial, InvoiceDTO buffer) {
		
		invoiceService.update(initial, buffer, new AsyncCallback<InvoiceDTO>() {
			
			@Override
			public void onSuccess(InvoiceDTO result) {
				// invoice supplier changed. Update EditSupplierParent window
				Long initialId = initial.getSupplier().getId();
				Long currentId = result.getSupplier().getId();
				if(!(initialId.equals(currentId))){
					view.getEditSupplierView().removeInvoice(result);
				} 
				// invoice has the same supplier
				else{
					view.getEditSupplierView().updateInvoice(result);
				}	
			}
			
			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof BusinessException){
					view.getEditSupplierView().getEditInvoiceView().setData(initial);
					BusinessException exp = (BusinessException) caught;
					AlertMessageBox alertBox = new AlertMessageBox("Error", exp.getMessage());
					alertBox.show();
				}
			}
		});
		view.getEditSupplierView().getEditInvoiceView().hide();
	}

	@Override
	public void getAll() {
		supplierService.getAll(new AsyncCallback<List<SupplierDTO>>() {
			
			@Override
			public void onSuccess(List<SupplierDTO> result) {
				view.getEditSupplierView().getEditInvoiceView().setSuppliers(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				//TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public void getProductTypes() {
		productTypeService.getAll(new AsyncCallback<List<ProductTypeDTO>>() {
			
			@Override
			public void onSuccess(List<ProductTypeDTO> result) {
				view.getEditSupplierView().getEditInvoiceView().setProductTypes(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
