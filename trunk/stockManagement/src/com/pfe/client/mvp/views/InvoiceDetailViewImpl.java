package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.ShipmentDTO;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class InvoiceDetailViewImpl implements InvoiceDetailView {
	
	private InvoiceDTO invoice;
	private VerticalLayoutContainer detailsCon;
	
	private VerticalLayoutContainer shipmentContainer;
	private VerticalLayoutData layoutData;
	private Label supplierLabel;

	public InvoiceDetailViewImpl(){
		
		AccordionLayoutAppearance appearance = GWT.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);
		
		// details : Supplier tab
		supplierLabel = new Label();
		ContentPanel supplierPanel = new ContentPanel(appearance);
		supplierPanel.setBodyStyleName("rawText");
		supplierPanel.setHeadingText("Supplier");
		supplierPanel.add(supplierLabel);
		
		// details : Shipments tab
		ContentPanel shipmentPanel = new ContentPanel(appearance);
		shipmentPanel.setBodyStyleName("rawText");
		shipmentPanel.setHeadingText("Shipments - initial quantities");
		
		shipmentContainer = new VerticalLayoutContainer();
		layoutData = new VerticalLayoutData();
		layoutData.setMargins(new Margins(10, 5, 10, 5));
		shipmentPanel.add(shipmentContainer);

		detailsCon = new VerticalLayoutContainer();
		detailsCon.add(supplierPanel);
		detailsCon.add(shipmentPanel);
	}

	@Override
	public Widget asWidget() {
		return detailsCon;
	}

	@Override
	public void clearData() {
		

	}

	@Override
	public void setData(InvoiceDTO data) {
		this.invoice = data;
		supplierLabel.setText(invoice.getSupplier().getName());
		
		List<ShipmentDTO> shipments = invoice.getShipments();
		for(ShipmentDTO shipment : shipments){
			Label label = new Label();
			label.setText(shipment.getProductType().getName() + " : " +  shipment.getInitialQuantity() + " items");
			shipmentContainer.add(label, layoutData);
		}

	}

}
