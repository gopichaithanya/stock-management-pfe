package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.client.ui.properties.InvoiceProperties;
import com.pfe.shared.dto.InvoiceDto;
import com.pfe.shared.dto.SupplierDto;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class EditSupplierViewImpl extends Window implements EditSupplierView {

	private static final InvoiceProperties props = GWT
			.create(InvoiceProperties.class);

	private SupplierDto supplier;
	private TextField nameField;
	private HtmlEditor descriptionEditor;
	private Grid<InvoiceDto> grid;
	private ListStore<InvoiceDto> store;

	private SupplierPresenter presenter;

	public EditSupplierViewImpl() {

		// check box selection model
		IdentityValueProvider<InvoiceDto> identity = new IdentityValueProvider<InvoiceDto>();
		CheckBoxSelectionModel<InvoiceDto> sm = new CheckBoxSelectionModel<InvoiceDto>(
				identity);
		sm.setSelectionMode(SelectionMode.SINGLE);

		// column configuration
		int ratio = 1;
		ColumnConfig<InvoiceDto, Integer> codeCol = new ColumnConfig<InvoiceDto, Integer>(
				props.code(), ratio, "Code");
		ColumnConfig<InvoiceDto, Integer> shipCol = new ColumnConfig<InvoiceDto, Integer>(
				props.shipments(), ratio, "Shipments");

		List<ColumnConfig<InvoiceDto, ?>> columnConfigList = new ArrayList<ColumnConfig<InvoiceDto, ?>>();
		columnConfigList.add(sm.getColumn());
		columnConfigList.add(codeCol);
		columnConfigList.add(shipCol);
		ColumnModel<InvoiceDto> cm = new ColumnModel<InvoiceDto>(
				columnConfigList);
		store = new ListStore<InvoiceDto>(props.key());
		grid = new Grid<InvoiceDto>(store, cm);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.getView().setAutoFill(true);
		
		

	}

	/**
	 * HTML table
	 * 
	 * @return
	 */
	private native String getTableMarkup() /*-{
		return [
				'<table width=100% cellpadding=0 cellspacing=0>',
				'<tr><td class=name width=50%></td><td class=description  width=50%></td></tr>',
				'<tr><td class=grid colspan=2></td></tr>', '</table>'

		].join("");
	}-*/;

	@Override
	public void setPresenter(SupplierPresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setData(SupplierDto supplier) {
		clearData();
		this.supplier = supplier;
		List<InvoiceDto> invoices = supplier.getInvoices();
		if(invoices != null){
			store.addAll(invoices);
		}
	}

	@Override
	public void clearData() {
		store.clear();
		
	}

}
