package com.pfe.client.mvp.views;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.ExpandMode;

public class ProductTypeViewImpl implements ProductTypeView {
	
	private AccordionLayoutContainer detailsCon;
	private String data;
	private Label descriptionLabel;
	private Label nameLabel;
	
	public ProductTypeViewImpl(){
		
		detailsCon = new AccordionLayoutContainer();
		AccordionLayoutAppearance appearance = GWT
				.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);
		// details : Name tab
		descriptionLabel = new Label();
		nameLabel = new Label();
		ContentPanel namePanel = new ContentPanel(appearance);
		namePanel.setBodyStyleName("rawText");
		namePanel.setHeadingText("Product Type");
		namePanel.add(nameLabel);
		namePanel.setExpanded(true);

		// details : Description tab
		ContentPanel descPanel = new ContentPanel(appearance);
		descPanel.setBodyStyleName("rawText");
		descPanel.setHeadingText("Description");
		descPanel.add(descriptionLabel);
		descPanel.setExpanded(true);
		
		detailsCon.add(namePanel);
		detailsCon.add(descPanel);
		detailsCon.setExpandMode(ExpandMode.MULTI);
		
	}
	
	@Override
	public Widget asWidget() {
		String [] values = data.split(" \t\n\r\f");
		nameLabel.setText(values[0]);
		descriptionLabel.setText(values[1]);
		return detailsCon;
	}


	@Override
	public void clearData() {

	}

	@Override
	public void setData(String data) {
		this.data = data;
		
	}

}
