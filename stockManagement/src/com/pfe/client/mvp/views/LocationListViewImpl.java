package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.client.ui.GridToolbar;
import com.pfe.client.ui.ViewConstants;
import com.pfe.client.ui.properties.LocationProperties;
import com.pfe.shared.dto.LocationDTO;
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

public class LocationListViewImpl implements LocationListView {
	
	private static final LocationProperties props = GWT.create(LocationProperties.class);
	
	private LocationPresenter presenter;
	private Grid<LocationDTO> grid;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<LocationDTO>> loader;
	private PagingToolBar pagingToolBar;
	private ListStore<LocationDTO> store;

	private ConfirmMessageBox confirmBox;
	private VerticalLayoutContainer verticalCon;
	private GridToolbar toolbar;
	private EditLocationView editView;
	private CreateLocationView createView;
	
	public LocationListViewImpl(){
		// check box selection model
		IdentityValueProvider<LocationDTO> identity = new IdentityValueProvider<LocationDTO>();
		CheckBoxSelectionModel<LocationDTO> sm = new CheckBoxSelectionModel<LocationDTO>(identity);

		// column configuration
		int ratio = 1;
		ColumnConfig<LocationDTO, String> nameCol = new ColumnConfig<LocationDTO, String>(props.name(), ratio, "Name");
		ColumnConfig<LocationDTO, String> typeCol = new ColumnConfig<LocationDTO, String>(props.type(), 2 * ratio, "Type");
		List<ColumnConfig<LocationDTO, ?>> columnConfigList = new ArrayList<ColumnConfig<LocationDTO, ?>>();
		columnConfigList.add(sm.getColumn());
		columnConfigList.add(nameCol);
		columnConfigList.add(typeCol);
		
		ColumnModel<LocationDTO> cm = new ColumnModel<LocationDTO>(columnConfigList);
		store = new ListStore<LocationDTO>(props.key());

		// Grid
		grid = new Grid<LocationDTO>(store, cm) {
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
		grid.getSelectionModel().addSelectionHandler(new SelectionHandler<LocationDTO>() {
			
			@Override
			public void onSelection(SelectionEvent<LocationDTO> arg0) {
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
		toolbar.getAddBtn().addSelectHandler(new AddBtnHandler());
		toolbar.getEditBtn().addSelectHandler(new EditBtnHandler());
		toolbar.getDeleteBtn().addSelectHandler(new DeleteBtnHandler());
		toolbar.getEditBtn().setEnabled(false);
		toolbar.getDeleteBtn().setEnabled(false);
		
		verticalCon = new VerticalLayoutContainer();
		verticalCon.add(toolbar, new VerticalLayoutData(1, -1));
		verticalCon.add(grid, new VerticalLayoutData(1, 1));
		verticalCon.add(pagingToolBar, new VerticalLayoutData(1, ViewConstants.pagingBarHeight));

	}


	/**
	 * Add location handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class AddBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			createView = new CreateLocationViewImpl();
			createView.setPresenter(presenter);
			presenter.getLocationTypes();
			createView.show();
		}
	}
	
	/**
	 * Edit location handler
	 * 
	 * @author Alexandra
	 * 
	 */
	private class EditBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {

			if (grid.getSelectionModel().getSelectedItem() == null) {
				return;
			}
			editView = new EditLocationViewImpl();
			editView.setPresenter(presenter);

			LocationDTO location = grid.getSelectionModel().getSelectedItem();
			presenter.find(location.getId());
		}
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
			if (grid.getSelectionModel().getSelectedItems() == null || 
					grid.getSelectionModel().getSelectedItems().size() == 0) {
				return;
			}
			confirmBox = new ConfirmMessageBox("Delete", "Delete location(s)?");
			final HideHandler hideHandler = new HideHandler() {

				@Override
				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					String msg = btn.getHideButton().getText();
					if (msg.equals("Yes")) {
						
						List<LocationDTO> locations = grid.getSelectionModel().getSelectedItems();
						presenter.delete(locations);
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
			LocationDTO selected = store.get(row);
			presenter.displayDetailsView(selected);

		}
	}
	
	@Override
	public Widget asWidget() {
		return verticalCon;
	}

	@Override
	public void setPresenter(LocationPresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setData(List<LocationDTO> locations) {
		this.store.addAll(locations);
		
	}

	@Override
	public void setPagingLoader(
			PagingLoader<FilterPagingLoadConfig, PagingLoadResult<LocationDTO>> remoteLoader) {
		loader = remoteLoader;
		pagingToolBar.bind(loader);
		pagingToolBar.first();
		pagingToolBar.enable();
		
	}

	@Override
	public ListStore<LocationDTO> getData() {
		return this.store;
	}

	@Override
	public EditLocationView getEditView() {
		return editView;
	}

	@Override
	public CreateLocationView getCreateView() {
		return this.createView;
	}

	@Override
	public void addData(LocationDTO location) {
		store.add(location);
		
	}
	
	@Override
	public void refreshGrid() {
		pagingToolBar.refresh();

	}

	@Override
	public void deleteData(List<LocationDTO> locations) {
		for(LocationDTO location : locations){
			store.remove(location);
		}
		
	}

}
