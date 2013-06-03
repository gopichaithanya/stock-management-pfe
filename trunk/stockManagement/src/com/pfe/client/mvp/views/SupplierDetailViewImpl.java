package com.pfe.client.mvp.views;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.ExpandMode;

public class SupplierDetailViewImpl implements SupplierDetailView {
	
	private AccordionLayoutContainer detailsCon;
	private SupplierDTO supplier;
	private Label descriptionLabel;
	private Label nameLabel;

	public SupplierDetailViewImpl(){
		
		detailsCon = new AccordionLayoutContainer();
		AccordionLayoutAppearance appearance = GWT
				.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);
		// details : Name tab
		descriptionLabel = new Label();
		nameLabel = new Label();
		ContentPanel namePanel = new ContentPanel(appearance);
		namePanel.setBodyStyleName("rawText");
		namePanel.setHeadingText("Supplier");
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
		nameLabel.setText(supplier.getName());
		descriptionLabel.setText(supplier.getDescription());
		//TODO add debt and invoice info
		return detailsCon;
	}

	@Override
	public void clearData() {

	}

	@Override
	public void setData(SupplierDTO supplier) {
		this.supplier = supplier;

	}

}
