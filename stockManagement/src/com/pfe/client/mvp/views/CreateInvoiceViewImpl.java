package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.client.ui.images.ImageResources;
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
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.IntegerPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class CreateInvoiceViewImpl extends Window implements CreateInvoiceView {
	
	private static final ShipmentProperties shipProps = GWT.create(ShipmentProperties.class);
	private static final SupplierProperties supplierProps = GWT.create(SupplierProperties.class);
	private static final ProductTypeProperties typeProps = GWT.create(ProductTypeProperties.class);

	private InvoicePresenter presenter;
	
	private NumberField<Integer> codeField;
	private Grid<ShipmentDTO> grid;
	private GridInlineEditing<ShipmentDTO> editingGrid;
	private ListStore<ShipmentDTO> shipmentStore;
	private ListStore<ProductTypeDTO> typeStore;
	private ListStore<SupplierDTO> supplierStore;
	private ComboBox<SupplierDTO> supplierCombo;
	private ComboBoxCell<ProductTypeDTO> typeCombo;
	private SimpleComboBox<String> paymentCombo;
	
	public CreateInvoiceViewImpl(){
		setBodyBorder(false);
		setWidth(650);
		setHeight(500);
		setMinHeight(500);
		setModal(true);
		setResizable(false);
		setClosable(false);
		setHeadingText("New Invoice");

		VerticalPanel vp = new VerticalPanel();
		FramedPanel fpanel = new FramedPanel();
		HtmlLayoutContainer container = new HtmlLayoutContainer(getTableMarkup());
		fpanel.setWidget(container);
		fpanel.setHeaderVisible(false);
		fpanel.setBorders(false);

		codeField = new NumberField<Integer>(new IntegerPropertyEditor());
		codeField.addValidator(new EmptyValidator<Integer>());
		container.add(new FieldLabel(codeField, "Code"), new HtmlData(".code"));
		paymentCombo = new SimpleComboBox<String>(new StringLabelProvider<String>());
		paymentCombo.add(InvoiceDTO.IMMEDIATE_PAY);
		paymentCombo.add(InvoiceDTO.ONSALE_PAY);
		paymentCombo.addValidator(new EmptyValidator<String>());
		container.add(new FieldLabel(paymentCombo, "Payment Type"),new HtmlData(".payment"));
		
		// Supplier combo
		supplierStore = new ListStore<SupplierDTO>(supplierProps.key());
		supplierCombo = new ComboBox<SupplierDTO>(supplierStore, supplierProps.nameLabel());
		supplierCombo.setWidth(180);
		supplierCombo.setTypeAhead(true);
		supplierCombo.setForceSelection(true);
		supplierCombo.setTriggerAction(TriggerAction.ALL);
		container.add(new FieldLabel(supplierCombo, "Supplier"), new HtmlData(".supplier"));
		
		// Column configuration
		int ratio = 1;
		ColumnConfig<ShipmentDTO, ProductTypeDTO> typeCol = new ColumnConfig<ShipmentDTO, ProductTypeDTO>(
				shipProps.productType(), 2 * ratio, "Type");
		ColumnConfig<ShipmentDTO, Double> priceCol = new ColumnConfig<ShipmentDTO, Double>(
				shipProps.unitPrice(), ratio, "Unit price");
		ColumnConfig<ShipmentDTO, Integer> initQtyCol = new ColumnConfig<ShipmentDTO, Integer>(
				shipProps.initialQty(), ratio, "Init. Qty");
		ColumnConfig<ShipmentDTO, Integer> currentQtyCol = new ColumnConfig<ShipmentDTO, Integer>(
				shipProps.currentQty(), ratio, "Current Qty");
		ColumnConfig<ShipmentDTO, Boolean> paidCol = new ColumnConfig<ShipmentDTO, Boolean>(
				shipProps.paid(), ratio, "Paid");
		ColumnConfig<ShipmentDTO, Date> dateCol = new ColumnConfig<ShipmentDTO, Date>(
				shipProps.created(), 2 * ratio, "Created");
		
		// Product type combo
		typeStore = new ListStore<ProductTypeDTO>(typeProps.key());
		typeCombo = new ComboBoxCell<ProductTypeDTO>(typeStore, typeProps.nameLabel());
		typeCombo.setTriggerAction(TriggerAction.ALL);
		typeCombo.setForceSelection(true);
		typeCombo.setWidth(120);
		 
		// Check box selection model
		IdentityValueProvider<ShipmentDTO> identity = new IdentityValueProvider<ShipmentDTO>();
		CheckBoxSelectionModel<ShipmentDTO> sm = new CheckBoxSelectionModel<ShipmentDTO>(identity);
		sm.setSelectionMode(SelectionMode.SINGLE);
		
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
		TextButton addBtn = new TextButton("Add", ImageResources.INSTANCE.addCreateIcon());
		TextButton deleteBtn = new TextButton("Delete", ImageResources.INSTANCE.addDeleteIcon());
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
		NumberField<Double> priceField = new NumberField<Double>(new NumberPropertyEditor.DoublePropertyEditor());
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
	 * Add shipment handler
	 * 
	 * @author Alexandra
	 *
	 */
	private class AddBtnHandler implements SelectHandler{

		@Override
		public void onSelect(SelectEvent event) {
			ShipmentDTO shipment = new ShipmentDTO();
			shipment.setProductType(typeStore.get(0));
			shipment.setUnitPrice(new Double(0));
			shipment.setInitialQuantity(0);	
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
			List<ShipmentDTO> shipments = grid.getSelectionModel().getSelectedItems();
			for(ShipmentDTO shipment : shipments){
				shipmentStore.remove(shipment);
			}
		}
	}
	
	/**
	 * Save invoice 
	 * 
	 * @author Alexandra
	 * 
	 */
	private class SubmitBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			
			if(!paymentCombo.isValid() || !codeField.isValid()){
				AlertMessageBox alertBox = new AlertMessageBox("Bad input", 
						"Please provide correct values for code and payment type");
				alertBox.show();
				return;
			}
			
			//commit changes on grid
			shipmentStore.commitChanges();
			ArrayList<ShipmentDTO> shipments = new ArrayList<ShipmentDTO>();
			shipments.addAll(shipmentStore.getAll());
			
			for(ShipmentDTO shipment : shipments){
				if(shipment.getInitialQuantity() == 0){
					AlertMessageBox alertBox = new AlertMessageBox("Bat input", 
							"Shipment's initial quantity must be greater than 0.");
					alertBox.show();
					return;
				}
			}
			
			InvoiceDTO invoice = new InvoiceDTO();
			invoice.setCode(codeField.getValue());
			invoice.setPaymentType(paymentCombo.getValue());
			invoice.setSupplier(supplierCombo.getValue());
			for(ShipmentDTO shipment : shipments){
				shipment.setInvoice(invoice);
			}
			invoice.setShipments(shipments);
			presenter.create(invoice);
			
			
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
	
	
	@Override
	public void setPresenter(InvoicePresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setProductTypes(List<ProductTypeDTO> types) {
		this.typeStore.addAll(types);

	}

	@Override
	public void setSuppliers(List<SupplierDTO> suppliers) {
		this.supplierStore.addAll(suppliers);
		supplierCombo.setValue(suppliers.get(0));
	}

	@Override
	public void clearData() {
		codeField.clear();
		typeStore.clear();
		supplierStore.clear();
		shipmentStore.clear();
	}
	
	/**
	 * HTML table
	 * 
	 * @return
	 */
	private native String getTableMarkup() /*-{
		return [
				'<table width=100% cellpadding=10 cellspacing=20>',
				'<tr><td class=code width=20%></td><td class=supplier width=50%></td><td class=payment width=20%></td></tr>',
				'<tr><td class=shipments colspan=3></tr>', '</table>'

		].join("");
	}-*/;

}
