package com.pfe.client.mvp.views;

import com.pfe.client.mvp.views.images.ImageResources;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.data.shared.ListStore;
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
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * Generic layout consisting of a BorderLayoutContainer with two panels: center
 * and west and a tool bar. The center panel contains a grid. The west panel has
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

	private TextButton addBtn;
	private TextButton editBtn;
	private TextButton deleteBtn;

	public GridBorderLayout(ListStore<T> store, ColumnModel<T> cm) {

		con = new BorderLayoutContainer();
		con.setResize(true);
		
		//TODO fix this hard coded height
		con.setHeight("200");

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
		grid = new Grid<T>(store, cm);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.getView().setAutoFill(false);
		grid.setBorders(false);
		grid.setColumnReordering(true);
		grid.setStateful(true);
		grid.setLayoutData(new VerticalLayoutData(1, 1));
		grid.getView().setAutoFill(true);

		// tool bar
		addBtn = new TextButton("Add", ImageResources.INSTANCE.addCreateIcon());
		editBtn = new TextButton("Edit", ImageResources.INSTANCE.addEditItcon());
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

		center.add(verticalCon);
	}

	public BorderLayoutContainer getCon() {
		return con;
	}

	public void setCon(BorderLayoutContainer con) {
		this.con = con;
	}

	public ContentPanel getWest() {
		return west;
	}

	public void setWest(ContentPanel west) {
		this.west = west;
	}

	public ContentPanel getCenter() {
		return center;
	}

	public void setCenter(ContentPanel center) {
		this.center = center;
	}

	public AccordionLayoutContainer getDetailsCon() {
		return detailsCon;
	}

	public void setDetailsCon(AccordionLayoutContainer detailsCon) {
		this.detailsCon = detailsCon;
	}

	public Grid<T> getGrid() {
		return grid;
	}

	public void setGrid(Grid<T> grid) {
		this.grid = grid;
	}

	public TextButton getAddBtn() {
		return addBtn;
	}

	public void setAddBtn(TextButton addBtn) {
		this.addBtn = addBtn;
	}

	public TextButton getEditBtn() {
		return editBtn;
	}

	public void setEditBtn(TextButton editBtn) {
		this.editBtn = editBtn;
	}

	public TextButton getDeleteBtn() {
		return deleteBtn;
	}

	public void setDeleteBtn(TextButton deleteBtn) {
		this.deleteBtn = deleteBtn;
	}

}
