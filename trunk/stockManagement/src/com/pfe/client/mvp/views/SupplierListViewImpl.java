package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.SupplierPresenter;

public class SupplierListViewImpl implements SupplierListView {
	
	private SupplierPresenter presenter;
	
	public SupplierListViewImpl(){
		
	}

	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPresenter(SupplierPresenter presenter) {
		this.presenter = presenter;

	}

}
