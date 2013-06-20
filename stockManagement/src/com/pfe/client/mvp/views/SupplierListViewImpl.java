package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.SupplierPresenter;
import com.pfe.client.ui.GridToolbar;
import com.pfe.client.ui.properties.SupplierProperties;
import com.pfe.shared.dto.SupplierDTO;
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
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

public class SupplierListViewImpl implements SupplierListView {

	private static final SupplierProperties props = GWT.create(SupplierProperties.class);

	private SupplierPresenter presenter;
	private Grid<SupplierDTO> grid;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SupplierDTO>> loader;
	private PagingToolBar pagingToolBar;
	private ListStore<SupplierDTO> store;

	private ConfirmMessageBox confirmBox;
	private VerticalLayoutContainer verticalCon;
	private GridToolbar toolbar;
	
	private CreateSupplierView createView;
	private EditSupplierView editView;

	public SupplierListViewImpl() {
		
		// Check box selection model
		IdentityValueProvider<SupplierDTO> identity = new IdentityValueProvider<SupplierDTO>();
		CheckBoxSelectionModel<SupplierDTO> sm = new CheckBoxSelectionModel<SupplierDTO>(identity);

		// Column configuration
		int ratio = 1;
		ColumnConfig<SupplierDTO, String> nameCol = new ColumnConfig<SupplierDTO, String>(props.name(), ratio, "Name");
		ColumnConfig<SupplierDTO, String> descCol = new ColumnConfig<SupplierDTO, String>(props.description(), 3 * ratio, "Description");

		List<ColumnConfig<SupplierDTO, ?>> columnConfigList = new ArrayList<ColumnConfig<SupplierDTO, ?>>();
		columnConfigList.add(sm.getColumn());
		columnConfigList.add(nameCol);
		columnConfigList.add(descCol);
		ColumnModel<SupplierDTO> cm = new ColumnModel<SupplierDTO>(columnConfigList);
		store = new ListStore<SupplierDTO>(props.key());

		// Grid
		grid = new Grid<SupplierDTO>(store, cm) {
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
		grid.getView().setAutoFill(true);
		grid.addRowClickHandler(new GridRowClickHandler());
		pagingToolBar = new PagingToolBar(4);

		toolbar = new GridToolbar();
		verticalCon = new VerticalLayoutContainer();
		verticalCon.add(toolbar, new VerticalLayoutData(1, -1));
		verticalCon.add(grid, new VerticalLayoutData(1, 1));
		verticalCon.add(pagingToolBar, new VerticalLayoutData(1, 35));
		
		grid.getSelectionModel().addSelectionHandler(new SelectionHandler<SupplierDTO>() {
			
			@Override
			public void onSelection(SelectionEvent<SupplierDTO> arg0) {
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

		toolbar.getAddBtn().addSelectHandler(new AddBtnHandler());
		toolbar.getEditBtn().addSelectHandler(new EditBtnHandler());
		toolbar.getDeleteBtn().addSelectHandler(new DeleteBtnHandler());
		toolbar.getEditBtn().setEnabled(false);
		toolbar.getDeleteBtn().setEnabled(false);

	}
	
	/**
	 * Add new supplier handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class AddBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			if (createView == null) {
				createView = new CreateSupplierViewImpl();
				createView.setPresenter(presenter);
			}
			createView.clearData();
			createView.show();
		}
	}
	
	/**
	 * Edit supplier handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class EditBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			displayEditSupplierWindow();
		}
	}
	
	/**
	 * Loads and displays edit window
	 * 
	 */
	private void displayEditSupplierWindow(){
		
		editView = new EditSupplierViewImpl();
		editView.setPresenter(presenter);
		SupplierDTO supplier = grid.getSelectionModel().getSelectedItem();
		presenter.find(supplier.getId());
	}
	
	
	/**
	 * Delete supplier handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class DeleteBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			
			if (grid.getSelectionModel().getSelectedItems() == null
					|| grid.getSelectionModel().getSelectedItems().size() == 0) { // double check for selected item
				return;
			}
			confirmBox = new ConfirmMessageBox("Delete","Delete supplier(s)?");
			final HideHandler hideHandler = new HideHandler() {

				@Override
				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					String msg = btn.getHideButton().getText();
					if (msg.equals("Yes")) {
						List<SupplierDTO> suppliers = grid.getSelectionModel().getSelectedItems();
						maskGrid();
						presenter.delete(suppliers);

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
	 * Click handler on grid row
	 * 
	 * @author Alexandra
	 * 
	 */
	private class GridRowClickHandler implements RowClickHandler {

		@Override
		public void onRowClick(RowClickEvent event) {
			int row = event.getRowIndex();
			SupplierDTO selected = store.get(row);
			presenter.displayDetailsView(selected);

		}
	}

	@Override
	public Widget asWidget() {
		return verticalCon;
	}

	@Override
	public void setPresenter(SupplierPresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setData(List<SupplierDTO> suppliers) {
		this.store.addAll(suppliers);
		
	}

	@Override
	public ListStore<SupplierDTO> getData() {
		return this.store;
	}

	@Override
	public void clearData() {
		store.clear();
		
	}

	@Override
	public void addData(SupplierDTO supplier) {
		store.add(supplier);
		
	}

	@Override
	public void updateData(SupplierDTO supplier) {
		store.update(supplier);
		
	}

	@Override
	public void deleteData(List<SupplierDTO> suppliers) {
		for(SupplierDTO supplier : suppliers){
			store.remove(supplier);
		}	
		
	}

	@Override
	public void setPagingLoader(PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SupplierDTO>> remoteLoader) {
		loader = remoteLoader;
		pagingToolBar.bind(loader);
		pagingToolBar.first();
		pagingToolBar.enable();
		
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
	public CreateSupplierView getCreateView() {
		return createView;
	}

	@Override
	public void refreshGrid() {
		pagingToolBar.refresh();
		
	}

	@Override
	public EditSupplierView getEditSupplierView() {
		return editView;
	}

}
