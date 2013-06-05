package com.pfe.client.mvp.views;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.shared.dto.InvoiceDTO;
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
	private Label invoiceLabel;

	public SupplierDetailViewImpl(){
		
		detailsCon = new AccordionLayoutContainer();
		AccordionLayoutAppearance appearance = GWT
				.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);
		// details : Name tab
		nameLabel = new Label();
		ContentPanel namePanel = new ContentPanel(appearance);
		namePanel.setBodyStyleName("rawText");
		namePanel.setHeadingText("Supplier");
		namePanel.add(nameLabel);
		namePanel.setExpanded(true);

		// details : Description tab
		descriptionLabel = new Label();
		ContentPanel descPanel = new ContentPanel(appearance);
		descPanel.setBodyStyleName("rawText");
		descPanel.setHeadingText("Description");
		descPanel.add(descriptionLabel);
		descPanel.setExpanded(true);
		
		// details : Description tab
		invoiceLabel = new Label();
		ContentPanel invoicePanel = new ContentPanel(appearance);
		invoicePanel.setBodyStyleName("rawText");
		invoicePanel.setHeadingText("Invoices");
		invoicePanel.add(invoiceLabel);
		invoicePanel.setExpanded(true);
		
		detailsCon.add(namePanel);
		detailsCon.add(descPanel);
		detailsCon.add(invoicePanel);
		detailsCon.setExpandMode(ExpandMode.MULTI);
	}
	
	@Override
	public Widget asWidget() {
		nameLabel.setText(supplier.getName());
		descriptionLabel.setText(supplier.getDescription());
		ArrayList<InvoiceDTO> invoices = supplier.getInvoices();
		int size = invoices.size();
		String text = "Total invoices : " + size + ". ";
		if (size > 0) {
			int debt = 0;
			for (InvoiceDTO invoice : invoices) {
				int restToPay = invoice.getRestToPay();
				if (restToPay > 0) {
					debt += restToPay;
				}
			}
			text += " Total debt : " + debt + ".";
		}
		invoiceLabel.setText(text);
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