package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.client.ui.properties.InvoiceProperties;
import com.pfe.shared.dto.InvoiceDTO;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;

public class InvoiceListViewImpl implements InvoiceListView {
	
	private static final InvoiceProperties props = GWT
			.create(InvoiceProperties.class);

	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPresenter(InvoicePresenter presenter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setData(List<InvoiceDTO> invoices) {
		// TODO Auto-generated method stub

	}

	@Override
	public ListStore<InvoiceDTO> getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addData(InvoiceDTO invoice) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateData(InvoiceDTO invoice) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteData(InvoiceDTO invioce) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPagingLoader(
			PagingLoader<FilterPagingLoadConfig, PagingLoadResult<InvoiceDTO>> remoteLoader) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void maskGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unmaskGrid() {
		// TODO Auto-generated method stub

	}

}
