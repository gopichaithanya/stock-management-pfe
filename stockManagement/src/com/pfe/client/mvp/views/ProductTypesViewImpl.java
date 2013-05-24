package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.client.mvp.views.images.ImageResources;
import com.pfe.client.mvp.views.properties.ProductTypeProperties;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.ExpandMode;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class ProductTypesViewImpl implements ProductTypesView {

	private static final ProductTypeProperties props = GWT
			.create(ProductTypeProperties.class);

	private ProductTypePresenter presenter;
	private ListStore<ProductType> store;
	private Grid<ProductType> grid;
	private Label descriptionLabel;
	private Label nameLabel;

	private BorderLayoutContainer con;
	private CreateProductTypeViewImpl createWindow;
	private EditProductTypeViewImpl editWindow;

	public ProductTypesViewImpl() {

		con = new BorderLayoutContainer();
		con.setResize(true);
		con.setId("borderlayoutContainer");
		con.setHeight("200");

		// West panel
		ContentPanel west = new ContentPanel();
		AccordionLayoutContainer detailsCon = new AccordionLayoutContainer();
		detailsCon.setExpandMode(ExpandMode.MULTI);
		AccordionLayoutAppearance appearance = GWT
				.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);

		// Description tab
		descriptionLabel = new Label();
		nameLabel = new Label();
		ContentPanel namePanel = new ContentPanel(appearance);
		namePanel.setBodyStyleName("rawText");
		namePanel.setHeadingText("Product Type");
		namePanel.add(nameLabel);
		namePanel.setExpanded(true);
		detailsCon.add(namePanel);

		// Description tab
		ContentPanel descPanel = new ContentPanel(appearance);
		descPanel.setBodyStyleName("rawText");
		descPanel.setHeadingText("Description");
		descPanel.add(descriptionLabel);
		descPanel.setExpanded(true);
		detailsCon.add(descPanel);

		west.add(detailsCon);
		west.setBorders(true);
		west.setHeadingHtml("Details");
		
		// center panel
		ContentPanel center = new ContentPanel();
		center.setBorders(true);
		center.setHeadingHtml("Product Types");

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
		int ratio = 1;
		ColumnConfig<ProductType, String> nameCol = new ColumnConfig<ProductType, String>(
				props.name(), ratio, "Name");
		ColumnConfig<ProductType, String> descCol = new ColumnConfig<ProductType, String>(
				props.description(), 3 * ratio, "Description");

		IdentityValueProvider<ProductType> identity = new IdentityValueProvider<ProductType>();
		CheckBoxSelectionModel<ProductType> sm = new CheckBoxSelectionModel<ProductType>(
				identity);
		sm.setSelectionMode(SelectionMode.SINGLE);

		List<ColumnConfig<ProductType, ?>> l = new ArrayList<ColumnConfig<ProductType, ?>>();
		l.add(sm.getColumn());
		l.add(nameCol);
		l.add(descCol);

		ColumnModel<ProductType> cm = new ColumnModel<ProductType>(l);
		store = new ListStore<ProductType>(props.key());
		grid = new Grid<ProductType>(store, cm);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.getView().setAutoFill(false);
		grid.setBorders(false);
		grid.setColumnReordering(true);
		grid.setStateful(true);
		grid.setLayoutData(new VerticalLayoutData(1, 1));
		grid.getView().setAutoFill(true);
		grid.addRowClickHandler(new GridRowClickHandler());

		// ToolBar
		TextButton addBtn = new TextButton("Add", ImageResources.INSTANCE.addCreateIcon());
		TextButton edit = new TextButton("Edit", ImageResources.INSTANCE.addEditItcon());
		TextButton deleteBtn = new TextButton("Delete", ImageResources.INSTANCE.addDeleteIcon());
		addBtn.addSelectHandler(new AddBtnHandler());
		edit.addSelectHandler(new EditBtnHandler());
		ToolBar toolbar = new ToolBar();
		toolbar.setSpacing(5);
		toolbar.setPadding(new Padding(5));
		toolbar.add(addBtn);
		toolbar.add(new SeparatorToolItem());
		toolbar.add(edit);
		toolbar.add(new SeparatorToolItem());
		toolbar.add(deleteBtn);
		
		VerticalLayoutContainer verticalCon = new VerticalLayoutContainer();
		verticalCon.add(toolbar, new VerticalLayoutData(1, -1));
		verticalCon.add(grid, new VerticalLayoutData(1, 1));
		
		center.add(verticalCon);
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
			ProductType selected = store.get(row);
			nameLabel.setText(selected.getName());
			descriptionLabel.setText(selected.getDescription());

		}
	}
	
	/**
	 * Add new type handler
	 * 
	 * @author Alexandra
	 *
	 */
	private class AddBtnHandler implements SelectHandler{

		@Override
		public void onSelect(SelectEvent event) {
			if(createWindow == null){
				createWindow = new CreateProductTypeViewImpl();
				createWindow.setPresenter(presenter);
			}
			createWindow.clearData();
			createWindow.show();
		}
		
	}
	
	/**
	 * Edit type handler
	 * 
	 * @author Alexandra
	 *
	 */
	private class EditBtnHandler implements SelectHandler{

		@Override
		public void onSelect(SelectEvent event) {
			if(editWindow == null){
				editWindow = new EditProductTypeViewImpl();
				editWindow.setPresenter(presenter);
			}
			ProductType productType = grid.getSelectionModel().getSelectedItem();
			if(productType != null){
				editWindow.setData(productType);
				editWindow.show();
			}
		}
	}

	@Override
	public Widget asWidget() {
		return con;
	}

	@Override
	public void setPresenter(ProductTypePresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setData(List<ProductType> productTypes) {
		this.store.addAll(productTypes);

	}

	@Override
	public List<ProductType> getData() {
		return this.store.getAll();
	}

	@Override
	public void clearData() {
		store.clear();

	}

	@Override
	public void addData(ProductType productType) {
		store.add(productType);
	}

	@Override
	public void updateData(ProductType productType) {
		store.update(productType);
		
	}

	@Override
	public CreateProductTypeViewImpl getCreateWindow() {
		return createWindow;
	}

	@Override
	public EditProductTypeViewImpl getEditWindow() {
		return editWindow;
	}

}
