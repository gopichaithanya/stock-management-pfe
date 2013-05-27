package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.client.mvp.views.properties.ProductTypeProperties;
import com.pfe.shared.model.ProductType;
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

public class ProductTypesViewImpl implements ProductTypesView {

	private static final ProductTypeProperties props = GWT
			.create(ProductTypeProperties.class);

	private ProductTypePresenter presenter;
	private Grid<ProductType> grid;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductType>> loader;
	private PagingToolBar pagingToolBar;
	private ListStore<ProductType> store;

	private ConfirmMessageBox confirmBox;
	private VerticalLayoutContainer verticalCon;
	
	private CreateProductTypeViewImpl createWindow;
	private EditProductTypeViewImpl editWindow;

	public ProductTypesViewImpl() {

		// check box selection model
		IdentityValueProvider<ProductType> identity = new IdentityValueProvider<ProductType>();
		CheckBoxSelectionModel<ProductType> sm = new CheckBoxSelectionModel<ProductType>(
				identity);
		sm.setSelectionMode(SelectionMode.SINGLE);

		// column configuration
		int ratio = 1;
		ColumnConfig<ProductType, String> nameCol = new ColumnConfig<ProductType, String>(
				props.name(), ratio, "Name");
		ColumnConfig<ProductType, String> descCol = new ColumnConfig<ProductType, String>(
				props.description(), 3 * ratio, "Description");

		List<ColumnConfig<ProductType, ?>> columnConfigList = new ArrayList<ColumnConfig<ProductType, ?>>();
		columnConfigList.add(sm.getColumn());
		columnConfigList.add(nameCol);
		columnConfigList.add(descCol);
		ColumnModel<ProductType> cm = new ColumnModel<ProductType>(
				columnConfigList);
		store = new ListStore<ProductType>(props.key());
			
		grid = new Grid<ProductType>(store, cm){
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
		grid.addRowClickHandler(new GridRowClickHandler());
		pagingToolBar = new PagingToolBar(2);

		GridToolbar toolbar = new GridToolbar();
		verticalCon = new VerticalLayoutContainer();
		verticalCon.add(toolbar, new VerticalLayoutData(1, -1));
		verticalCon.add(grid, new VerticalLayoutData(1, 1));
		verticalCon.add(pagingToolBar, new VerticalLayoutData(1, 35));

		toolbar.getAddBtn().addSelectHandler(new AddBtnHandler());
		toolbar.getEditBtn().addSelectHandler(new EditBtnHandler());
		toolbar.getDeleteBtn().addSelectHandler(new DeleteBtnHandler());

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
			if (createWindow == null) {
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
	private class EditBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			if (editWindow == null) {
				editWindow = new EditProductTypeViewImpl();
				editWindow.setPresenter(presenter);
			}
			ProductType productType = grid.getSelectionModel()
					.getSelectedItem();
			if (productType != null) {
				editWindow.setData(productType);
				editWindow.show();
			}
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

			confirmBox = new ConfirmMessageBox("Quit",
					"Are you sure you want to delete the type?");
			final HideHandler hideHandler = new HideHandler() {

				@Override
				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					String msg = btn.getHideButton().getText();
					if (msg.equals("Yes")) {
						
						ProductType productType = grid
								.getSelectionModel().getSelectedItem();
						if (productType != null) {
							//layout.maskGrid();
							presenter.deleteProductType(productType);
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

	@Override
	public Widget asWidget() {
		return verticalCon;
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
	public ListStore<ProductType> getData() {
		return this.store;
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
	public void deleteData(ProductType productType) {
		store.remove(productType);
	}

	@Override
	public CreateProductTypeViewImpl getCreateWindow() {
		return createWindow;
	}

	@Override
	public EditProductTypeViewImpl getEditWindow() {
		return editWindow;
	}
	
	public void setLoader(
			PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ProductType>> loader) {
		this.loader = loader;
	}

	public void bindPagingToolBar() {
		pagingToolBar.bind(loader);
	}

	public void refreshGrid(){
		pagingToolBar.refresh();
	}

}
