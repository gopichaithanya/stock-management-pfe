package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.ProductTypePresenter;
import com.pfe.client.mvp.views.properties.ProductTypeProperties;
import com.pfe.shared.model.ProductType;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;

public class ProductTypesViewImpl implements ProductTypesView {

	private static final ProductTypeProperties props = GWT
			.create(ProductTypeProperties.class);

	private ProductTypePresenter presenter;

	private GridBorderLayout<ProductType> layout;
	private ListStore<ProductType> store;
	private Label descriptionLabel;
	private Label nameLabel;
	private ConfirmMessageBox confirmBox;

	private CreateProductTypeViewImpl createWindow;
	private EditProductTypeViewImpl editWindow;

	public ProductTypesViewImpl() {

		AccordionLayoutAppearance appearance = GWT
				.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);
		// details : Name tab
		descriptionLabel = new Label();
		nameLabel = new Label();
		ContentPanel namePanel = new ContentPanel(appearance);
		namePanel.setBodyStyleName("rawText");
		namePanel.setHeadingText("Product Type");
		namePanel.add(nameLabel);
		namePanel.setExpanded(true);

		// details : Description tab
		ContentPanel descPanel = new ContentPanel(appearance);
		descPanel.setBodyStyleName("rawText");
		descPanel.setHeadingText("Description");
		descPanel.add(descriptionLabel);
		descPanel.setExpanded(true);

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

		layout = new GridBorderLayout<ProductType>(store, cm);
		layout.getGrid().addRowClickHandler(new GridRowClickHandler());
		layout.getWest().setHeadingHtml("Details");
		layout.getCenter().setHeadingHtml("Product Types");
		layout.addDetailsTab(namePanel);
		layout.addDetailsTab(descPanel);
		layout.getAddBtn().addSelectHandler(new AddBtnHandler());
		layout.getEditBtn().addSelectHandler(new EditBtnHandler());
		layout.getDeleteBtn().addSelectHandler(new DeleteBtnHandler());

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
			ProductType productType = layout.getGrid().getSelectionModel()
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
						
						ProductType productType = layout.getGrid()
								.getSelectionModel().getSelectedItem();
						if (productType != null) {
							layout.maskGrid();
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
		return layout.getCon();
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

	@Override
	public GridBorderLayout<ProductType> getLayout() {
		return layout;
	}

}
