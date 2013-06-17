package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.client.ui.GridToolbar;
import com.pfe.client.ui.properties.InvoiceProperties;
import com.pfe.shared.dto.InvoiceDTO;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

public class InvoiceListViewImpl implements InvoiceListView {
	
	private static final InvoiceProperties props = GWT.create(InvoiceProperties.class);
	
	private InvoicePresenter presenter;
	private Grid<InvoiceDTO> grid;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<InvoiceDTO>> loader;
	private PagingToolBar pagingToolBar;
	private ListStore<InvoiceDTO> store;

	private ConfirmMessageBox confirmBox;
	private VerticalLayoutContainer verticalCon;
	private GridToolbar toolbar;
	private CheckBox checkBox;
	
	private CreateInvoiceView createView;
	private EditInvoiceView editView;
	
	public InvoiceListViewImpl(){
		// check box selection model
		IdentityValueProvider<InvoiceDTO> identity = new IdentityValueProvider<InvoiceDTO>();
		CheckBoxSelectionModel<InvoiceDTO> sm = new CheckBoxSelectionModel<InvoiceDTO>(identity);

		// column configuration
		int ratio = 1;
		ColumnConfig<InvoiceDTO, Integer> codeCol = new ColumnConfig<InvoiceDTO, Integer>(
				props.code(), ratio, "Code");
		ColumnConfig<InvoiceDTO, String> supplierCol = new ColumnConfig<InvoiceDTO, String>(
				props.supplier(), 2 * ratio, "Supplier");
		ColumnConfig<InvoiceDTO, String> paymentCol = new ColumnConfig<InvoiceDTO, String>(
				props.payment(), ratio, "Payment");
		ColumnConfig<InvoiceDTO, Double> debtCol = new ColumnConfig<InvoiceDTO, Double>(
				props.restToPay(), ratio, "Debt");
		ColumnConfig<InvoiceDTO, Date> dateCol = new ColumnConfig<InvoiceDTO, Date>(
				props.created(), 3 * ratio, "Created on");
		DateCell dateCell = new DateCell(DateTimeFormat.getFormat("dd/MM/yyyy HH:mm:ss"));
		dateCol.setCell(dateCell);
		
		List<ColumnConfig<InvoiceDTO, ?>> columnConfigList = new ArrayList<ColumnConfig<InvoiceDTO, ?>>();
		columnConfigList.add(sm.getColumn());
		columnConfigList.add(codeCol);
		columnConfigList.add(supplierCol);
		columnConfigList.add(paymentCol);
		columnConfigList.add(debtCol);
		columnConfigList.add(dateCol);
		ColumnModel<InvoiceDTO> cm = new ColumnModel<InvoiceDTO>(columnConfigList);
		store = new ListStore<InvoiceDTO>(props.key());

		// Grid
		grid = new Grid<InvoiceDTO>(store, cm) {
			
			@Override
			protected void onDoubleClick(Event e) {
				refreshEditView();
				//Element target = Element.as(e.getEventTarget());
				//int rowIndex = view.findRowIndex(target);
			}
			
			@Override
			protected void onAfterFirstAttach() {
				super.onAfterFirstAttach();
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						loader.load();
					}
				});
			}
		};
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.setBorders(false);
		grid.setColumnReordering(true);
		grid.setStateful(true);
		grid.getView().setAutoFill(true);
		grid.setHeight("100%");
		grid.getSelectionModel().addSelectionHandler(new SelectionHandler<InvoiceDTO>() {
			
			@Override
			public void onSelection(SelectionEvent<InvoiceDTO> arg0) {
				if (grid.getSelectionModel().getSelectedItems().size() > 1) { // if more then one item is selected, enable only the delete button
					toolbar.getEditBtn().setEnabled(false);
					toolbar.getDeleteBtn().setEnabled(true);
				} else if (grid.getSelectionModel().getSelectedItems().size() == 1) { // if one item is selected, enable delete and edit buttons
					toolbar.getEditBtn().setEnabled(true);
					toolbar.getDeleteBtn().setEnabled(true);
				} else { // if no selection, then disable the edit and delete buttons
					toolbar.getEditBtn().setEnabled(false);
					toolbar.getDeleteBtn().setEnabled(false);
				}
			}
		});
		pagingToolBar = new PagingToolBar(4);

		toolbar = new GridToolbar();
		verticalCon = new VerticalLayoutContainer();
		verticalCon.add(toolbar, new VerticalLayoutData(1, -1));
		verticalCon.add(grid, new VerticalLayoutData(1, 1));
		verticalCon.add(pagingToolBar, new VerticalLayoutData(1, 35));

		toolbar.getAddBtn().addSelectHandler(new AddBtnHandler());
		toolbar.getEditBtn().addSelectHandler(new EditBtnHandler());
		toolbar.getDeleteBtn().addSelectHandler(new DeleteBtnHandler());
		toolbar.getEditBtn().setEnabled(false);
		toolbar.getDeleteBtn().setEnabled(false);
		checkBox = new CheckBox();
		checkBox.setBoxLabel("Show paid");
		checkBox.addValueChangeHandler(new CheckBoxHandler());
		toolbar.addTool(checkBox);
		
		// toolbar.getFilterBtn().addSelectHandler(new FilterBtnHandler());
		// toolbar.getClearFilterBtn().addSelectHandler(
		// new ClearFilterBtnHandler());
	}


	/**
	 * Add new invoice handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class AddBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			if (createView == null) {
				createView = new CreateInvoiceViewImpl();
				createView.setPresenter(presenter);
			}
			createView.clearData();
			//Get available types and suppliers for combo selection
			presenter.getProductTypes("create");
			presenter.getSuppliers("create");
			createView.show();
		}
	}
	
	/**
	 * Edit invoice handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class EditBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			refreshEditView();
			
		}
	}
	
	/**
	 * Sets EditView data
	 * 
	 */
	@Override
	public void refreshEditView(){
		if (grid.getSelectionModel().getSelectedItem() == null) { // double check for selected item
			return;
		}
		editView = new EditInvoiceViewImpl();
		editView.setPresenter(presenter);
		
		//Get available suppliers for combo selection
		presenter.getSuppliers("edit");
		
		//Get available product types 
		presenter.getProductTypes("edit");
		
		//Get invoice
		InvoiceDTO invoice = grid.getSelectionModel().getSelectedItem();
		presenter.find(invoice.getId());
	}
	
	/**
	 * Delete invoice handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class DeleteBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			if (grid.getSelectionModel().getSelectedItem() == null) { // double check for selected item
				return;
			}

			confirmBox = new ConfirmMessageBox("Delete", "Are you sure you want to delete the invoice?");
			final HideHandler hideHandler = new HideHandler() {

				@Override
				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					String msg = btn.getHideButton().getText();
					if (msg.equals("Yes")) {

						List<InvoiceDTO> invoices = grid.getSelectionModel().getSelectedItems();
						if (invoices != null) {
							maskGrid();
							presenter.delete(invoices);
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
	
	private class CheckBoxHandler implements ValueChangeHandler<Boolean>{

		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			Boolean checked = event.getValue();
			if(checked){
				presenter.search();
			} else{
				presenter.searchUnpaid();
			}
			
		}
		
	}
	
	@Override
	public Widget asWidget() {
		return verticalCon;
	}

	@Override
	public void setPresenter(InvoicePresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setData(List<InvoiceDTO> invoices) {
		this.store.addAll(invoices);

	}

	@Override
	public ListStore<InvoiceDTO> getData() {
		return this.store;
	}

	@Override
	public void clearData() {
		store.clear();

	}

	@Override
	public void addData(InvoiceDTO invoice) {
		store.add(invoice);

	}

	@Override
	public void updateData(InvoiceDTO invoice) {
		store.update(invoice);

	}

	@Override
	public void deleteData(List<InvoiceDTO> invoices) {
		for(InvoiceDTO invoice : invoices){
			store.remove(invoice);
		}

	}

	@Override
	public void setPagingLoader(
			PagingLoader<FilterPagingLoadConfig, PagingLoadResult<InvoiceDTO>> remoteLoader) {
		loader = remoteLoader;
		pagingToolBar.bind(loader);
		pagingToolBar.first();
		pagingToolBar.enable();

	}

	@Override
	public void refreshGrid() {
		pagingToolBar.refresh();

	}

	@Override
	public void maskGrid() {
		grid.mask("Loading... Please wait.");

	}

	@Override
	public void unmaskGrid() {
		grid.unmask();

	}

	@Override
	public EditInvoiceView getEditView() {
		return this.editView;
	}

	@Override
	public CreateInvoiceView getCreateView() {
		return this.createView;
	}

}
