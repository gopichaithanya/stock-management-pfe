package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.client.ui.CloseWindowButonHandler;
import com.pfe.client.ui.properties.InvoiceProperties;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
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
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class EditSupplierViewImpl extends Window implements EditSupplierView {

	private static final InvoiceProperties props = GWT.create(InvoiceProperties.class);

	private SupplierDTO supplier;
	private TextField nameField;
	private HtmlEditor descriptionEditor;
	private Grid<InvoiceDTO> grid;
	private ListStore<InvoiceDTO> store;
	private EditInvoiceView editInvoiceView;

	private SupplierPresenter presenter;

	public EditSupplierViewImpl() {

		setWidth(850);
		setHeight(350);
		setMinHeight(350);
		setModal(true);
		setResizable(false);
		setClosable(false);

		// Column configuration
		int ratio = 1;
		ColumnConfig<InvoiceDTO, Integer> codeCol = new ColumnConfig<InvoiceDTO, Integer>(
				props.code(), ratio, "Code");
		ColumnConfig<InvoiceDTO, String> paymentCol = new ColumnConfig<InvoiceDTO, String>(
				props.payment(), 2 * ratio, "Payment");
		ColumnConfig<InvoiceDTO, Double> debtCol = new ColumnConfig<InvoiceDTO, Double>(
				props.restToPay(), 2 * ratio, "Rest to pay");
		ColumnConfig<InvoiceDTO, Date> dateCol = new ColumnConfig<InvoiceDTO, Date>(
				props.created(), 4 * ratio, "Created");
		ColumnConfig<InvoiceDTO, String> detailCol = new ColumnConfig<InvoiceDTO, String>(
				props.supplier(), 2 * ratio, "Details");

		TextButtonCell detailBtn = new TextButtonCell();
		detailBtn.setText("Details");
		detailBtn.addSelectHandler(new DetailsBtnHandler());
		detailCol.setCell(detailBtn);
		DateCell dateCell = new DateCell(DateTimeFormat.getFormat("dd/MM/yyyy HH:mm:ss"));
		dateCol.setCell(dateCell);

		List<ColumnConfig<InvoiceDTO, ?>> columnConfigList = new ArrayList<ColumnConfig<InvoiceDTO, ?>>();
		columnConfigList.add(codeCol);
		columnConfigList.add(paymentCol);
		columnConfigList.add(debtCol);
		columnConfigList.add(dateCol);
		columnConfigList.add(detailCol);
		ColumnModel<InvoiceDTO> cm = new ColumnModel<InvoiceDTO>(columnConfigList);
		store = new ListStore<InvoiceDTO>(props.key());

		grid = new Grid<InvoiceDTO>(store, cm);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.getView().setAutoFill(true);
		grid.setBorders(true);
		grid.setHeight(150);
		grid.setWidth(450);
		
		VerticalPanel vp = new VerticalPanel();
		FramedPanel fpanel = new FramedPanel();
		HtmlLayoutContainer container = new HtmlLayoutContainer(getTableMarkup());
		fpanel.setWidget(container);
		fpanel.setHeaderVisible(false);
		fpanel.setBorders(false);
		
		nameField = new TextField();
		nameField.addValidator(new MinLengthValidator(2));
		nameField.addValidator(new EmptyValidator<String>());
		nameField.setWidth(300);
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
		cancelBtn.addSelectHandler(new CloseWindowButonHandler(this));

		fpanel.setButtonAlign(BoxLayoutPack.CENTER);
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
				supplier.setDescription(descriptionEditor.getValue());
				supplier.setName(nameField.getValue());
				presenter.update(supplier);
			}
		}
	}

	/**
	 * Open invoice detail window
	 * 
	 * @author Alexandra
	 * 
	 */
	private class DetailsBtnHandler implements SelectHandler {
		
		@Override
		public void onSelect(SelectEvent event) {
			Context c = event.getContext();
			int row = c.getIndex();
			InvoiceDTO invoice = store.get(row);	
			if (editInvoiceView == null) {
				editInvoiceView = new EditInvoiceViewImpl();
				editInvoiceView.setPresenter(presenter);
			}
			
			//Get available suppliers for combo selection
			presenter.getAll();
			//Get available types
			presenter.getProductTypes();
			editInvoiceView.setData(invoice);
			editInvoiceView.show();
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
				'<tr><td class=description width=50%> <td class=grid width=50%></td></tr>',
				'</table>'

		].join("");
	}-*/;

	@Override
	public void setPresenter(SupplierPresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setData(SupplierDTO supplier) {
		clearData();
		this.supplier = supplier;
		List<InvoiceDTO> invoices = supplier.getInvoices();
		if (invoices != null) {
			store.addAll(invoices);
		}
		nameField.setValue(supplier.getName());
		descriptionEditor.setValue(supplier.getDescription());
		setHeadingText(supplier.getName() + " Supplier");
	}

	@Override
	public void clearData() {
		store.clear();
		nameField.clear();
		descriptionEditor.clear();
	}

	@Override
	public EditInvoiceView getEditInvoiceView() {
		return editInvoiceView;
	}

	@Override
	public void removeInvoice(InvoiceDTO invoice) {
		store.remove(invoice);
	}

	@Override
	public void updateInvoice(InvoiceDTO invoice) {
		store.update(invoice);
		
	}

	@Override
	public SupplierDTO gettData() {
		return supplier;
	}
}
