package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;
import com.pfe.client.mvp.presenters.ProductTypesPresenter;
import com.pfe.shared.model.ProductType;

public class ProductTypesViewImpl implements ProductTypesView {
	
	private ProductTypesPresenter presenter;
	private List<ProductType> pTypes = new ArrayList<ProductType>();

	private VerticalPanel vp;
	
	public ProductTypesViewImpl(){
		vp = new VerticalPanel();
		vp.setPixelSize(300, 300);
	}
	
	@Override
	public Widget asWidget() {
		clearData();
		
		//TODO replace this with grid
		for(ProductType p:pTypes){
			TextButton l = new TextButton(p.getDescription());
			vp.add(l);
		}
		
		return vp;
	}

	@Override
	public void setPresenter(ProductTypesPresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setData(List<ProductType> productTypes) {
		this.pTypes = productTypes;

	}

	@Override
	public List<ProductType> getData() {
		return this.pTypes;
	}

	@Override
	public void clearData() {
		//clear grid store here

	}

}
