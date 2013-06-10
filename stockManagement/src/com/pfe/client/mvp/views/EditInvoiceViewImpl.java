package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.client.mvp.presenters.Presenter;
import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.client.ui.properties.ProductTypeProperties;
import com.pfe.client.ui.properties.ShipmentProperties;
import com.pfe.client.ui.properties.SupplierProperties;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.ShipmentDTO;
import com.pfe.shared.dto.SupplierDTO;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.IntegerPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class EditInvoiceViewImpl extends Window implements EditInvoiceView {

	private static final ShipmentProperties shipProps = GWT
			.create(ShipmentProperties.class);
	private static final SupplierProperties supplierProps = GWT
			.create(SupplierProperties.class);
	private static final ProductTypeProperties typeProps = GWT
			.create(ProductTypeProperties.class);

	private Presenter presenter;

	private InvoiceDTO invoice;
	private NumberField<Integer> codeField;
	private DateField dateField;
	private TextField paymentField;
	private NumberField<Double> debtField;
	private NumberField<Double> fractionField;
	private TextField supplierField;
	private TextButton payBtn;
	private Grid<ShipmentDTO> grid;
	private GridInlineEditing<ShipmentDTO> editingGrid;
	private ListStore<ShipmentDTO> shipmentStore;
	private ListStore<SupplierDTO> supplierStore;
	private ListStore<ProductTypeDTO> typeStore;
	private ComboBox<SupplierDTO> supplierCombo;
	private ComboBoxCell<ProductTypeDTO> typeCombo;
	private ConfirmMessageBox confirmBox;

	public EditInvoiceViewImpl() {
		setBodyBorder(false);
		setWidth(650);
		setHeight(500);
		setMinHeight(500);
		setModal(true);
		setResizable(false);
		setClosable(false);

		VerticalPanel vp = new VerticalPanel();
		FramedPanel fpanel = new FramedPanel();
		HtmlLayoutContainer container = new HtmlLayoutContainer(getTableMarkup());
		fpanel.setWidget(container);
		fpanel.setHeaderVisible(false);
		fpanel.setBorders(false);

		codeField = new NumberField<Integer>(new IntegerPropertyEditor());
		codeField.setReadOnly(true);
		container.add(new FieldLabel(codeField, "Code"), new HtmlData(".code"));
		dateField = new DateField();
		container.add(new FieldLabel(dateField, "Created On"), new HtmlData(".date"));
		paymentField = new TextField();
		paymentField.setReadOnly(true);
		container.add(new FieldLabel(paymentField, "Payment Type"),new HtmlData(".payment"));
		
		// Supplier combo
		supplierField = new TextField();
		supplierStore = new ListStore<SupplierDTO>(supplierProps.key());
		supplierCombo = new ComboBox<SupplierDTO>(supplierStore, supplierProps.nameLabel());
		supplierCombo.setEmptyText("Select a supplier...");
		supplierCombo.setWidth(200);
		supplierCombo.setTypeAhead(true);
		supplierCombo.setTriggerAction(TriggerAction.ALL);
		container.add(new FieldLabel(supplierCombo, "Supplier"), new HtmlData(".supplier"));
		
		debtField = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
		debtField.setReadOnly(true);
		container.add(new FieldLabel(debtField, "Rest to pay"), new HtmlData(".debt"));
		fractionField = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
		container.add(new FieldLabel(fractionField, "Debt fraction"), new HtmlData(".fr"));
		payBtn = new TextButton("Pay");
		payBtn.addSelectHandler(new PayBtnHandler());
		container.add(new FieldLabel(payBtn, "Pay fraction"), new HtmlData(".pay"));
		
		// Column configuration
		int ratio = 1;
		ColumnConfig<ShipmentDTO, ProductTypeDTO> typeCol = new ColumnConfig<ShipmentDTO, ProductTypeDTO>(
				shipProps.productType(), 3 * ratio, "Type");
		ColumnConfig<ShipmentDTO, Integer> priceCol = new ColumnConfig<ShipmentDTO, Integer>(
				shipProps.unitPrice(), ratio, "Unit price");
		ColumnConfig<ShipmentDTO, Integer> initQtyCol = new ColumnConfig<ShipmentDTO, Integer>(
				shipProps.initialQty(), ratio, "Init. Qty");
		ColumnConfig<ShipmentDTO, Integer> currentQtyCol = new ColumnConfig<ShipmentDTO, Integer>(
				shipProps.currentQty(), ratio, "Current Qty");
		ColumnConfig<ShipmentDTO, Boolean> paidCol = new ColumnConfig<ShipmentDTO, Boolean>(
				shipProps.paid(), ratio, "Paid");
		ColumnConfig<ShipmentDTO, Date> dateCol = new ColumnConfig<ShipmentDTO, Date>(
				shipProps.created(), 3 * ratio, "Created");
		
		// Product type combo
		typeStore = new ListStore<ProductTypeDTO>(typeProps.key());
		typeCombo = new ComboBoxCell<ProductTypeDTO>(typeStore, typeProps.nameLabel());
		typeCombo.setTriggerAction(TriggerAction.ALL);
		typeCombo.setForceSelection(true);
		typeCombo.setWidth(170);
		 
		// Check box selection model
		IdentityValueProvider<ShipmentDTO> identity = new IdentityValueProvider<ShipmentDTO>();
		CheckBoxSelectionModel<ShipmentDTO> sm = new CheckBoxSelectionModel<ShipmentDTO>(identity);
		sm.setSelectionMode(SelectionMode.MULTI);
		
		List<ColumnConfig<ShipmentDTO, ?>> columnConfigList = new ArrayList<ColumnConfig<ShipmentDTO, ?>>();
		columnConfigList.add(sm.getColumn());
		columnConfigList.add(typeCol);
		columnConfigList.add(priceCol);
		columnConfigList.add(initQtyCol);
		columnConfigList.add(currentQtyCol);
		columnConfigList.add(paidCol);
		columnConfigList.add(dateCol);
		typeCol.setCell(typeCombo);
		ColumnModel<ShipmentDTO> cm = new ColumnModel<ShipmentDTO>(columnConfigList);
		shipmentStore = new ListStore<ShipmentDTO>(shipProps.key());

		grid = new Grid<ShipmentDTO>(shipmentStore, cm);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.getView().setAutoFill(true);
		grid.setBorders(true);
		grid.setHeight(200);
		
		VerticalLayoutContainer gridPanel = new VerticalLayoutContainer();
		ToolBar toolBar = new ToolBar();
		TextButton addBtn = new TextButton("Add");
		TextButton deleteBtn = new TextButton("Delete");
		addBtn.addSelectHandler(new AddBtnHandler());
		deleteBtn.addSelectHandler(new DeleteBtnHandler());
		toolBar.add(addBtn);
		toolBar.add(deleteBtn);
		gridPanel.add(toolBar, new VerticalLayoutData(1, -1));
		gridPanel.add(grid, new VerticalLayoutData(1, 1));

		FieldLabel gridField = new FieldLabel(gridPanel, "Shipments");
		container.add(gridField, new HtmlData(".shipments"));
		
		//Editing fields
		editingGrid = new GridInlineEditing<ShipmentDTO>(grid);
		NumberField<Integer> priceField = new NumberField<Integer>(new IntegerPropertyEditor());
//		priceField.addParseErrorHandler(new ParseErrorHandler() {
//			@Override
//			public void onParseError(ParseErrorEvent event) {
//				 Info.display("Parse Error", event.getErrorValue() + " could not be parsed as a number");
//				
//			}
//		});
//		priceField.setAllowBlank(false);
//		priceField.addValidator(new MinNumberValidator<Integer>(1));
		editingGrid.addEditor(priceCol, priceField);
		editingGrid.addEditor(initQtyCol, new NumberField<Integer>(new IntegerPropertyEditor()));
		editingGrid.setClicksToEdit(ClicksToEdit.TWO);
		
		TextButton cancelBtn = new TextButton("Cancel");
		TextButton submitBtn = new TextButton("Save");
		submitBtn.addSelectHandler(new SubmitBtnHandler());
		cancelBtn.addSelectHandler(new CancelBtnHandler(this));
		fpanel.setButtonAlign(BoxLayoutPack.CENTER);
		fpanel.addButton(submitBtn);
		fpanel.addButton(cancelBtn);
		List<FieldLabel> labels = FormPanelHelper.getFieldLabels(fpanel);
		for (FieldLabel lbl : labels) { lbl.setLabelAlign(LabelAlign.TOP);}

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

			invoice.setCode(codeField.getValue());
			invoice.setCreated(dateField.getValue());
			invoice.setPaymentType(paymentField.getValue());
			invoice.setSupplier(supplierCombo.getValue());
			invoice.setRestToPay(debtField.getValue());
			//commit changes on grid
			shipmentStore.commitChanges();
			ArrayList<ShipmentDTO> shipments = new ArrayList<ShipmentDTO>();
			shipments.addAll(shipmentStore.getAll());
			invoice.setShipments(shipments);
			
			if (presenter instanceof SupplierPresenter) {
				((SupplierPresenter) presenter).updateInvoice(invoice);
			} else if (presenter instanceof InvoicePresenter) {

			}
		}
	}
	
	/**
	 * Pay part of debt
	 * 
	 * @author Alexandra
	 * 
	 */
	private class PayBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			double fraction = fractionField.getValue();
			if(fraction >= debtField.getValue()){
				debtField.setValue(new Double(0));
			}
			else{
				debtField.setValue(debtField.getValue() - fraction);
			}
			fractionField.clear();
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
	 * Add shipment handler
	 * 
	 * @author Alexandra
	 *
	 */
	private class AddBtnHandler implements SelectHandler{

		@Override
		public void onSelect(SelectEvent event) {
			//TODO finish this
			ShipmentDTO shipment = new ShipmentDTO();
			shipment.setProductType(typeStore.get(0));
			shipment.setUnitPrice(0);
			shipment.setInitialQuantity(0);	
			shipment.setInvoice(invoice);
			
			editingGrid.cancelEditing();
		    shipmentStore.add(0, shipment);
		    editingGrid.startEditing(new GridCell(0, 0));	
		}
	}
	
	/**
	 * Delete shipment handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class DeleteBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {

			confirmBox = new ConfirmMessageBox("Delete","Are you sure you want to delete the type?");
			final HideHandler hideHandler = new HideHandler() {

				@Override
				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					String msg = btn.getHideButton().getText();
					if (msg.equals("Yes")) {

						List<ShipmentDTO> shipments = grid.getSelectionModel().getSelectedItems();
						if (shipments.size() > 0) {
							if (presenter instanceof SupplierPresenter) {
								((SupplierPresenter) presenter).deleteShipments(shipments);
							} else if (presenter instanceof InvoicePresenter) {

							}
						}

					} else if (msg.equals("No")) {
						confirmBox.hide();
					}
				}
			};
			confirmBox.addHideHandler(hideHandler);
			confirmBox.show();
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
		fractionField.clear();
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

	@Override
	public void setProductTypes(List<ProductTypeDTO> types) {
		typeStore.clear();
		typeStore.addAll(types);
		
	}

	@Override
	public void deleteShipments(List<ShipmentDTO> shipments) {
		for(ShipmentDTO shipment : shipments){
			shipmentStore.remove(shipment);
		}
		
	}
}
