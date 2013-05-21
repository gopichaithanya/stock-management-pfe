package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;
import com.pfe.client.mvp.presenters.ProductTypesPresenter;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;

public class ProductTypesViewImpl implements ProductTypesView {

	private ProductTypesPresenter presenter;
	private List<ProductType> pTypes = new ArrayList<ProductType>();

	private BorderLayoutContainer con;
	private ContentPanel west;
	private ContentPanel center;

	public ProductTypesViewImpl() {

		con = new BorderLayoutContainer();
		con.setResize(true);
		
		west = new ContentPanel();
		center = new ContentPanel();

		west.setBorders(true);
		center.setBorders(true);
		
		MarginData centerData = new MarginData();
		centerData.setMargins(new Margins(0, 0, 0, 0));
		
		BorderLayoutData eastData = new BorderLayoutData(150);
		eastData.setMargins(new Margins(0, 0, 0, 5));
		eastData.setSplit(true);
		eastData.setCollapsible(true);
		
		con.setEastWidget(west, eastData);
		con.setCenterWidget(center, centerData);

	}

	@Override
	public Widget asWidget() {
		clearData();

		// TODO replace this with grid
		for (ProductType p : pTypes) {
			TextButton l = new TextButton(p.getDescription());
			//west.add(l);
		}

		return con;
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
		// clear grid store here

	}

}
