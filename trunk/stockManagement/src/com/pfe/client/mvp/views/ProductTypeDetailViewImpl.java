package com.pfe.client.mvp.views;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

public class ProductTypeDetailViewImpl implements ProductTypeDetailView {
	
	private VerticalLayoutContainer detailsCon;
	private String data;
	private Label descriptionLabel;
	private Label nameLabel;
	
	public ProductTypeDetailViewImpl(){
		
		detailsCon = new VerticalLayoutContainer();
		AccordionLayoutAppearance appearance = GWT.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);
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
		
	}
	
	@Override
	public Widget asWidget() {
		String [] values = data.split(" \t\n\r\f");
		nameLabel.setText(values[0]);
		descriptionLabel.setText(values[1].replaceAll("\\<[^>]*>",""));
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
