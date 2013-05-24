package com.pfe.client.mvp.views;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.IsWidget;
import com.pfe.client.mvp.views.images.ImageResources;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.ExpandMode;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * Generic layout consisting of a BorderLayoutContainer with two panels: center
 * and west and a tool bar. The center panel contains a paging grid. The west panel has
 * a container to display details
 * 
 * @author Alexandra
 * 
 */
public class GridBorderLayout<T> {

	private BorderLayoutContainer con;
	private ContentPanel west;
	private ContentPanel center;
	private AccordionLayoutContainer detailsCon;

	private Grid<T> grid;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<T>> loader;
	private PagingToolBar pagingToolBar;
	
	private TextButton addBtn;
	private TextButton editBtn;
	private TextButton deleteBtn;

	public GridBorderLayout(ListStore<T> store, ColumnModel<T> cm) {

		con = new BorderLayoutContainer();
		con.setResize(true);

		// West panel
		west = new ContentPanel();
		west.setBorders(true);
		detailsCon = new AccordionLayoutContainer();
		detailsCon.setExpandMode(ExpandMode.MULTI);
		west.add(detailsCon);

		// center panel
		center = new ContentPanel();
		center.setBorders(true);

		// center data
		MarginData centerData = new MarginData();
		centerData.setMargins(new Margins(0, 0, 0, 0));

		// west data
		BorderLayoutData westData = new BorderLayoutData(400);
		westData.setMargins(new Margins(0, 0, 0, 5));
		westData.setSplit(true);
		westData.setCollapsible(true);

		con.setEastWidget(west, westData);
		con.setCenterWidget(center, centerData);

		// grid
		grid = new Grid<T>(store, cm){
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
		grid.getView().setAutoFill(false);
		grid.setBorders(false);
		grid.setColumnReordering(true);
		grid.setStateful(true);
		grid.setLayoutData(new VerticalLayoutData(1, 1));
		grid.getView().setAutoFill(true);
		pagingToolBar = new PagingToolBar(2);

		// tool bar
		addBtn = new TextButton("Add", ImageResources.INSTANCE.addCreateIcon());
		editBtn = new TextButton("Edit", ImageResources.INSTANCE.addEditIcon());
		deleteBtn = new TextButton("Delete",
				ImageResources.INSTANCE.addDeleteIcon());

		ToolBar toolbar = new ToolBar();
		toolbar.setSpacing(5);
		toolbar.setPadding(new Padding(5));
		toolbar.add(addBtn);
		toolbar.add(new SeparatorToolItem());
		toolbar.add(editBtn);
		toolbar.add(new SeparatorToolItem());
		toolbar.add(deleteBtn);

		VerticalLayoutContainer verticalCon = new VerticalLayoutContainer();
		verticalCon.add(toolbar, new VerticalLayoutData(1, -1));
		verticalCon.add(grid, new VerticalLayoutData(1, 1));
		verticalCon.add(pagingToolBar, new VerticalLayoutData(1, 35));

		center.add(verticalCon);
	}

	/**
	 * Adds tabs to the details container
	 * 
	 * @param tab
	 */
	public void addDetailsTab(IsWidget tab){
		detailsCon.add(tab);
	}
	
	public void maskGrid(){
		grid.mask("Please wait...");
	}
	
	public void unmaskGrid(){
		grid.unmask();
	}
	
	public BorderLayoutContainer getCon() {
		return con;
	}

	public ContentPanel getWest() {
		return west;
	}

	public ContentPanel getCenter() {
		return center;
	}
	
	public Grid<T> getGrid() {
		return grid;
	}

	public TextButton getAddBtn() {
		return addBtn;
	}

	public TextButton getEditBtn() {
		return editBtn;
	}

	public TextButton getDeleteBtn() {
		return deleteBtn;
	}

	public void setLoader(
			PagingLoader<FilterPagingLoadConfig, PagingLoadResult<T>> loader) {
		this.loader = loader;
	}

	public void bindPagingToolBar() {
		pagingToolBar.bind(loader);
	}

	public void refreshGrid(){
		pagingToolBar.refresh();
	}
}
