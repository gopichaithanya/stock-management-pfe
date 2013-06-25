package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.client.ui.GridToolbar;
import com.pfe.client.ui.ViewConstants;
import com.pfe.client.ui.properties.ProductTypeProperties;
import com.pfe.shared.dto.ProductTypeDTO;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
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

public class ProductTypeListViewImpl implements ProductTypeListView {

	private static final ProductTypeProperties props = GWT.create(ProductTypeProperties.class);

	private ProductTypePresenter presenter;
	private Grid<ProductTypeDTO> grid;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductTypeDTO>> loader;
	private PagingToolBar pagingToolBar;
	private ListStore<ProductTypeDTO> store;

	private ConfirmMessageBox confirmBox;
	private VerticalLayoutContainer verticalCon;
	private GridToolbar toolbar;

	private CreateProductTypeView createView;
	private EditProductTypeView editView;

	public ProductTypeListViewImpl() {

		// check box selection model
		IdentityValueProvider<ProductTypeDTO> identity = new IdentityValueProvider<ProductTypeDTO>();
		CheckBoxSelectionModel<ProductTypeDTO> sm = new CheckBoxSelectionModel<ProductTypeDTO>(identity);
		sm.setSelectionMode(SelectionMode.SINGLE);

		// column configuration
		int ratio = 1;
		ColumnConfig<ProductTypeDTO, String> nameCol = new ColumnConfig<ProductTypeDTO, String>(props.name(), ratio, "Name");
		ColumnConfig<ProductTypeDTO, String> descCol = new ColumnConfig<ProductTypeDTO, String>(props.description(), 3 * ratio, "Description");

		List<ColumnConfig<ProductTypeDTO, ?>> columnConfigList = new ArrayList<ColumnConfig<ProductTypeDTO, ?>>();
		columnConfigList.add(sm.getColumn());
		columnConfigList.add(nameCol);
		columnConfigList.add(descCol);
		ColumnModel<ProductTypeDTO> cm = new ColumnModel<ProductTypeDTO>(columnConfigList);
		store = new ListStore<ProductTypeDTO>(props.key());

		//Grid
		grid = new Grid<ProductTypeDTO>(store, cm) {
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
		grid.getSelectionModel().addSelectionHandler(new SelectionHandler<ProductTypeDTO>() {
			
			@Override
			public void onSelection(SelectionEvent<ProductTypeDTO> arg0) {
				if (grid.getSelectionModel().getSelectedItems().size() > 1) { 
					toolbar.getEditBtn().setEnabled(false);
					toolbar.getDeleteBtn().setEnabled(true);
				} else if (grid.getSelectionModel().getSelectedItems().size() == 1) { 
					toolbar.getEditBtn().setEnabled(true);
					toolbar.getDeleteBtn().setEnabled(true);
				} else {
					toolbar.getEditBtn().setEnabled(false);
					toolbar.getDeleteBtn().setEnabled(false);
				}
			}
		});
		pagingToolBar = new PagingToolBar(ViewConstants.recordsPerPage);

		toolbar = new GridToolbar();
		verticalCon = new VerticalLayoutContainer();
		verticalCon.add(toolbar, new VerticalLayoutData(1, -1));
		verticalCon.add(grid, new VerticalLayoutData(1, 1));
		verticalCon.add(pagingToolBar, new VerticalLayoutData(1, ViewConstants.pagingBarHeight));

		toolbar.getAddBtn().addSelectHandler(new AddBtnHandler());
		toolbar.getEditBtn().addSelectHandler(new EditBtnHandler());
		toolbar.getDeleteBtn().addSelectHandler(new DeleteBtnHandler());
		toolbar.getFilterText().setEmptyText("Type name...");
		toolbar.getFilterBtn().addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				presenter.search();
				
			}
		});
		toolbar.getClearFilterBtn().addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				toolbar.getFilterText().clear();
				presenter.search();
				
			}
		});
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
			ProductTypeDTO selected = store.get(row);
			presenter.displayDetailsView(selected);

		}
	}

	/**
	 * Add new type handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class AddBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			if (createView == null) {
				createView = new CreateProductTypeViewImpl();
				createView.setPresenter(presenter);
			}
			createView.clearData();
			createView.show();
		}
	}

	/**
	 * Edit type handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class EditBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			ProductTypeDTO selected = grid.getSelectionModel().getSelectedItem();
			if (selected == null) {// check if item selected
				return;
			}
			if (editView == null) {
				editView = new EditProductTypeViewImpl();
				editView.setPresenter(presenter);
			}
			editView.setData(selected);
			editView.show();

		}
	}

	/**
	 * Delete type handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class DeleteBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			if (grid.getSelectionModel().getSelectedItems() == null ||
					grid.getSelectionModel().getSelectedItems().size() == 0) { 
				return;
			}

			confirmBox = new ConfirmMessageBox("Delete","Delete the type(s)?");
			final HideHandler hideHandler = new HideHandler() {

				@Override
				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					String msg = btn.getHideButton().getText();
					if (msg.equals("Yes")) {

						List<ProductTypeDTO> selected = grid.getSelectionModel().getSelectedItems();
						presenter.delete(selected);

					} else if (msg.equals("No")) {
						confirmBox.hide();
					}
				}
			};
			confirmBox.addHideHandler(hideHandler);
			confirmBox.show();

		}
	}
	
	@Override
	public Widget asWidget() {
		return verticalCon;
	}

	@Override
	public void setPresenter(ProductTypePresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setData(List<ProductTypeDTO> productTypes) {
		this.store.addAll(productTypes);
	}

	@Override
	public ListStore<ProductTypeDTO> getData() {
		return this.store;
	}

	@Override
	public void clearData() {
		store.clear();
	}

	@Override
	public void addData(ProductTypeDTO productType) {
		store.add(productType);
	}

	@Override
	public void updateData(ProductTypeDTO productType) {
		store.update(productType);
	}

	@Override
	public void deleteData(List<ProductTypeDTO> productTypes) {
		for(ProductTypeDTO type : productTypes){
			store.remove(type);
		}
		
	}

	@Override
	public CreateProductTypeView getCreateView() {
		return createView;
	}

	@Override
	public EditProductTypeView getEditView() {
		return editView;
	}

	@Override
	public void setPagingLoader(PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductTypeDTO>> remoteLoader) {
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
	public String getFilterValue() {
		return toolbar.getFilterText().getCurrentValue();
	}

}
