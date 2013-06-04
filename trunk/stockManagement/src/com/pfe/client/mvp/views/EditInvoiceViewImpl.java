package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.client.mvp.presenters.Presenter;
import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.client.ui.properties.ShipmentProperties;
import com.pfe.client.ui.properties.SupplierProperties;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.ShipmentDTO;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.IntegerPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class EditInvoiceViewImpl extends Window implements EditInvoiceView {
	
	private static final ShipmentProperties props = GWT
			.create(ShipmentProperties.class);
	private static final SupplierProperties supplierProps = GWT
			.create(SupplierProperties.class);

	private Presenter presenter;

	private InvoiceDTO invoice;
	private NumberField<Integer> codeField;
	private DateField dateField;
	private TextField paymentField;
	private NumberField<Integer> debtField;
	private NumberField<Integer> fractionField;
	private TextField supplierField;
	private TextButton payBtn;
	private Grid<ShipmentDTO> grid;
	private ListStore<ShipmentDTO> shipmentStore;
	private ListStore<SupplierDTO> supplierStore;
	private ComboBox<SupplierDTO> supplierCombo;

	public EditInvoiceViewImpl() {
		setBodyBorder(false);
		setWidth(670);
		setHeight(350);
		setMinHeight(350);
		setModal(true);
		setResizable(false);
		setClosable(false);

		VerticalPanel vp = new VerticalPanel();
		FramedPanel fpanel = new FramedPanel();
		HtmlLayoutContainer container = new HtmlLayoutContainer(
				getTableMarkup());
		fpanel.setWidget(container);
		fpanel.setHeaderVisible(false);
		fpanel.setBorders(false);

		codeField = new NumberField<Integer>(new IntegerPropertyEditor());
		codeField.isReadOnly();
		container.add(new FieldLabel(codeField, "Code"), new HtmlData(".code"));
		dateField = new DateField();
		container.add(new FieldLabel(dateField, "Created On"), new HtmlData(
				".date"));
		paymentField = new TextField();
		container.add(new FieldLabel(paymentField, "Payment Type"),
				new HtmlData(".payment"));
		
		supplierField = new TextField();
		supplierStore = new ListStore<SupplierDTO>(supplierProps.key());
		supplierCombo = new ComboBox<SupplierDTO>(supplierStore, supplierProps.nameLabel());
		supplierCombo.setEmptyText("Select a supplier...");
		supplierCombo.setWidth(200);
		supplierCombo.setTypeAhead(true);
		supplierCombo.setTriggerAction(TriggerAction.ALL);
		container.add(new FieldLabel(supplierCombo, "Supplier"), new HtmlData(
				".supplier"));
		
		debtField = new NumberField<Integer>(new IntegerPropertyEditor());
		container.add(new FieldLabel(debtField, "Rest to pay"), new HtmlData(".debt"));
		fractionField = new NumberField<Integer>(new IntegerPropertyEditor());
		container.add(new FieldLabel(fractionField, "Debt fraction"), new HtmlData(".fr"));
		payBtn = new TextButton("Pay");
		container.add(new FieldLabel(payBtn, "Pay fraction"), new HtmlData(".pay"));
		
		// column configuration
		int ratio = 1;
		ColumnConfig<ShipmentDTO, String> typeCol = new ColumnConfig<ShipmentDTO, String>(
				props.productType(), 2 * ratio, "Type");
		ColumnConfig<ShipmentDTO, Integer> priceCol = new ColumnConfig<ShipmentDTO, Integer>(
				props.unitPrice(), 2 * ratio, "Unit price");
		ColumnConfig<ShipmentDTO, Integer> initQtyCol = new ColumnConfig<ShipmentDTO, Integer>(
				props.initialQty(), 2 * ratio, "Init. Qty");
		ColumnConfig<ShipmentDTO, Integer> currentQtyCol = new ColumnConfig<ShipmentDTO, Integer>(
				props.currentQty(), 2 * ratio, "Current Qty");
		ColumnConfig<ShipmentDTO, Boolean> paidCol = new ColumnConfig<ShipmentDTO, Boolean>(
				props.paid(), ratio, "Paid");
		ColumnConfig<ShipmentDTO, Date> dateCol = new ColumnConfig<ShipmentDTO, Date>(
				props.created(), 3 * ratio, "Created");
		
		List<ColumnConfig<ShipmentDTO, ?>> columnConfigList = new ArrayList<ColumnConfig<ShipmentDTO, ?>>();
		columnConfigList.add(typeCol);
		columnConfigList.add(priceCol);
		columnConfigList.add(initQtyCol);
		columnConfigList.add(currentQtyCol);
		columnConfigList.add(paidCol);
		columnConfigList.add(dateCol);
		ColumnModel<ShipmentDTO> cm = new ColumnModel<ShipmentDTO>(columnConfigList);
		shipmentStore = new ListStore<ShipmentDTO>(props.key());

		grid = new Grid<ShipmentDTO>(shipmentStore, cm);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.getView().setAutoFill(true);
		grid.setBorders(true);
		grid.setHeight(60);
		
		FieldLabel gridField = new FieldLabel(grid, "Shipments");
		container.add(gridField, new HtmlData(".shipments"));
		
		TextButton cancelBtn = new TextButton("Cancel");
		TextButton submitBtn = new TextButton("Save");
		submitBtn.addSelectHandler(new SubmitBtnHandler());
		cancelBtn.addSelectHandler(new CancelBtnHandler(this));

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
			InvoiceDTO buffer = new InvoiceDTO();
			buffer.setCode(codeField.getValue());
			buffer.setCreated(dateField.getValue());
			buffer.setPaymentType(paymentField.getValue());
			buffer.setShipments(shipmentStore.getAll());
			buffer.setSupplier(supplierCombo.getValue());
			if(presenter instanceof SupplierPresenter){
				((SupplierPresenter)presenter).updateInvoice(invoice, buffer);
			} else if (presenter instanceof InvoicePresenter){
				
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

		public CancelBtnHandler(Window w) {
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
				'<tr><td class=code width=30%></td><td class=date width=40%></td><td class=payment width=30%></td> <td></td></tr>',
				'<tr><td class=supplier width=50%></td><td class=debt width=15%></td></td> <td class=fr width=20%></td><td class=pay width=15%></td></tr>',
				'<tr><td class=shipments colspan=4></tr>', '</table>'

		].join("");
	}-*/;

	@Override
	public void setData(InvoiceDTO invoice) {
		this.invoice = invoice;
		clearData();
		codeField.setValue(invoice.getCode());
		dateField.setValue(invoice.getCreated());
		paymentField.setValue(invoice.getPaymentType());
		debtField.setValue(invoice.getRestToPay());
		supplierField.setValue(invoice.getSupplier().getName());
		setHeadingText("Invoice " + invoice.getCode());
		shipmentStore.addAll(invoice.getShipments());
		
	}

	@Override
	public void clearData() {
		codeField.clear();
		dateField.clear();
		paymentField.clear();
		debtField.clear();
		supplierField.clear();
		shipmentStore.clear();

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void setSuppliers(List<SupplierDTO> suppliers) {
		supplierStore.clear();
		supplierStore.addAll(suppliers);
		supplierCombo.setValue(invoice.getSupplier());
	}

}
