package com.pfe.client.mvp.activities;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.presenters.ReportsPresenter;
import com.pfe.client.mvp.views.ReportsView;
import com.pfe.client.service.ProductTypeServiceAsync;
import com.pfe.client.service.StockServiceAsync;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.StockDTO;

public class ReportsActivity extends AbstractActivity implements ReportsPresenter {
	
	private ClientFactory clientFactory;
	private StockServiceAsync stockService;
	private ProductTypeServiceAsync productTypeService;
	
	private ReportsView view;
	
	public ReportsActivity(ClientFactory clientFactory){
		this.clientFactory = clientFactory;
		this.stockService = clientFactory.getStockService();
		this.productTypeService = clientFactory.getProductTypeService();
	}

	@Override
	public void bind() {
		view.setPresenter(this);

	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getReportsView();
		bind();
		productTypeService.getAll(new AsyncCallback<List<ProductTypeDTO>>() {
			
			@Override
			public void onSuccess(List<ProductTypeDTO> result) {
				view.setProductTypes(result);
				panel.setWidget(view.asWidget());
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void getStocks(ProductTypeDTO productType) {
		stockService.find(productType, new AsyncCallback<List<StockDTO>>() {
			
			@Override
			public void onSuccess(List<StockDTO> result) {
				view.setStocks(result);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
