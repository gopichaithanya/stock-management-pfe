package com.pfe.client.mvp.views;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.client.ui.properties.InvoiceProperties;
import com.pfe.shared.dto.InvoiceDto;
import com.pfe.shared.dto.SupplierDto;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
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
		
		setBodyBorder(false);
		setWidth(850);
		setHeight(350);
		setMinHeight(350);
		setModal(true);
		setResizable(false);
		setClosable(false);

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
		ColumnConfig<InvoiceDto, BigDecimal> debtCol = new ColumnConfig<InvoiceDto, BigDecimal>(
				props.restToPay(), ratio, "Rest to pay");
		ColumnConfig<InvoiceDto, Date> dateCol = new ColumnConfig<InvoiceDto, Date>(
				props.created(), 5 * ratio, "Created");
		
		List<ColumnConfig<InvoiceDto, ?>> columnConfigList = new ArrayList<ColumnConfig<InvoiceDto, ?>>();
		columnConfigList.add(sm.getColumn());
		columnConfigList.add(codeCol);
		columnConfigList.add(shipCol);
		columnConfigList.add(debtCol);
		columnConfigList.add(dateCol);
		ColumnModel<InvoiceDto> cm = new ColumnModel<InvoiceDto>(
				columnConfigList);
		store = new ListStore<InvoiceDto>(props.key());
		
		grid = new Grid<InvoiceDto>(store, cm);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.getView().setAutoFill(true);
		grid.setBorders(true);
		grid.setHeight(150);
		
		VerticalPanel vp = new VerticalPanel();
		FramedPanel fpanel = new FramedPanel();
		HtmlLayoutContainer container = new HtmlLayoutContainer(
				getTableMarkup());
		fpanel.setWidget(container);
		fpanel.setHeaderVisible(false);
		fpanel.setBorders(false);
		nameField = new TextField();
		nameField.addValidator(new MinLengthValidator(2));
		nameField.addValidator(new EmptyValidator<String>());
		container.add(new FieldLabel(nameField, "Name"), new HtmlData(".name"));
		descriptionEditor = new HtmlEditor();
		descriptionEditor.setHeight(150);
		FieldLabel descriptor = new FieldLabel(descriptionEditor, "Description");
		container.add(descriptor, new HtmlData(".description"));
		
		FieldLabel gridField = new FieldLabel(grid, "Invoices");
		container.add(gridField, new HtmlData(".grid"));
		
		TextButton cancelBtn = new TextButton("Cancel");
		TextButton submitBtn = new TextButton("Save");
		submitBtn.addSelectHandler(new SubmitBtnHandler());
		cancelBtn.addSelectHandler(new CancelBtnHandler(this));

		fpanel.addButton(submitBtn);
		fpanel.addButton(cancelBtn);
		List<FieldLabel> labels = FormPanelHelper.getFieldLabels(fpanel);
		for (FieldLabel lbl : labels) {
			lbl.setLabelAlign(LabelAlign.TOP);
		}
		vp.add(fpanel);
		this.add(vp);
	}
	
	/**
	 * Save updates
	 * 
	 * @author Alexandra
	 * 
	 */
	private class SubmitBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			if (nameField.isValid()) {
				SupplierDto updated = new SupplierDto();
				updated.setDescription(descriptionEditor.getValue());
				updated.setName(nameField.getValue());
				presenter.update(supplier, updated);
			}
		}
	}
	
	/**
	 * Close window
	 * 
	 * @author Alexandra
	 * 
	 */
	private class CancelBtnHandler implements SelectHandler {

		private Window w;
		public CancelBtnHandler(Window w){
			this.w = w;
		}
		
		@Override
		public void onSelect(SelectEvent event) {
			w.hide();
		}
	}
	
	

	/**
	 * HTML table
	 * 
	 * @return
	 */
	private native String getTableMarkup() /*-{
		return [
				'<table width=100% cellpadding=10 cellspacing=10>',
				'<tr><td class=name width=50%></td> <td width=50%></td></tr>',
				'<tr><td class=description width=50%> <td class=grid width=50%></td></tr>', '</table>'

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
		nameField.setValue(supplier.getName());
		descriptionEditor.setValue(supplier.getDescription());
		setHeadingText(supplier.getName());
	}

	@Override
	public void clearData() {
		store.clear();
		nameField.clear();
		descriptionEditor.clear();
		
	}

}
