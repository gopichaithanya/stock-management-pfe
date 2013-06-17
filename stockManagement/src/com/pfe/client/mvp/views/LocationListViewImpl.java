package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.client.ui.GridToolbar;
import com.pfe.client.ui.properties.LocationProperties;
import com.pfe.shared.dto.LocationDTO;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
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

	//private ConfirmMessageBox confirmBox;
	private VerticalLayoutContainer verticalCon;
	private GridToolbar toolbar;
	private EditLocationView editView;
	
	public LocationListViewImpl(){
		// check box selection model
		IdentityValueProvider<LocationDTO> identity = new IdentityValueProvider<LocationDTO>();
		CheckBoxSelectionModel<LocationDTO> sm = new CheckBoxSelectionModel<LocationDTO>(identity);

		// column configuration
		int ratio = 1;
		ColumnConfig<LocationDTO, String> nameCol = new ColumnConfig<LocationDTO, String>(
				props.name(), ratio, "Name");
		ColumnConfig<LocationDTO, String> typeCol = new ColumnConfig<LocationDTO, String>(
				props.type(), 2 * ratio, "Type");
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
		//grid.addRowClickHandler(new GridRowClickHandler());
		pagingToolBar = new PagingToolBar(4);
				
		toolbar = new GridToolbar();
		toolbar.getEditBtn().addSelectHandler(new EditBtnHandler());
		verticalCon = new VerticalLayoutContainer();
		verticalCon.add(toolbar, new VerticalLayoutData(1, -1));
		verticalCon.add(grid, new VerticalLayoutData(1, 1));
		verticalCon.add(pagingToolBar, new VerticalLayoutData(1, 35));

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
			if (editView == null) {
				editView = new EditLocationViewImpl();
				editView.setPresenter(presenter);
			}
			LocationDTO location = grid.getSelectionModel().getSelectedItem();
			presenter.find(location.getId());
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

}
