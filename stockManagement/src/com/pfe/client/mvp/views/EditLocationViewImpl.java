package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.client.ui.properties.StockProperties;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.StockDTO;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class EditLocationViewImpl extends Window implements EditLocationView {
	
	private static final StockProperties props = GWT.create(StockProperties.class);
	
	private LocationDTO location;
	private LocationPresenter presenter;
	private TextField nameField;
	private TextField typeField;
	private Grid<StockDTO> grid;
	private ListStore<StockDTO> store;
	
	private Window sellWindow;
	private NumberField<Integer> sellQtyField;
	private TextButton sellQtyBtn;
	
	private StockDTO selectedStock;

	public EditLocationViewImpl(){
		setModal(true);
		setMinHeight(430);
		
		VerticalPanel vp = new VerticalPanel();
		FramedPanel fpanel = new FramedPanel();
		HtmlLayoutContainer container = new HtmlLayoutContainer(getTableMarkup());
		fpanel.setWidget(container);
		fpanel.setHeaderVisible(false);
		fpanel.setBorders(false);
		
		nameField = new TextField();
		container.add(new FieldLabel(nameField, "Name"),new HtmlData(".name"));
		typeField = new TextField();
		container.add(new FieldLabel(typeField, "Type"),new HtmlData(".type"));
		
		//Stock grid
		int ratio = 1;
		ColumnConfig<StockDTO, String> typeCol = new ColumnConfig<StockDTO, String>(
				props.type(), 3 * ratio, "Type");
		ColumnConfig<StockDTO, Integer> qtyCol = new ColumnConfig<StockDTO, Integer>(
				props.quantity(), 3 * ratio, "Quantity");
		ColumnConfig<StockDTO, String> sellCol = new ColumnConfig<StockDTO, String>(
				props.type(), 2 * ratio, "Sell");
		ColumnConfig<StockDTO, String> shipCol = new ColumnConfig<StockDTO, String>(
				props.type(), 2 * ratio, "Ship");
		List<ColumnConfig<StockDTO, ?>> columnConfigList = new ArrayList<ColumnConfig<StockDTO, ?>>();
		columnConfigList.add(typeCol);
		columnConfigList.add(qtyCol);
		columnConfigList.add(sellCol);
		columnConfigList.add(shipCol);
		ColumnModel<StockDTO> cm = new ColumnModel<StockDTO>(columnConfigList);
		TextButtonCell sellBtn = new TextButtonCell();
		sellBtn.setText("Sell");
		sellBtn.addSelectHandler(new SellBtnHandler());
		sellCol.setCell(sellBtn);
		TextButtonCell shipBtn = new TextButtonCell();
		shipBtn.setText("Ship");
		shipCol.setCell(shipBtn);
		
		store = new ListStore<StockDTO>(props.key());
		grid = new Grid<StockDTO>(store, cm);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.getView().setAutoFill(true);
		grid.setHeight(200);
		grid.setBorders(true);
		VerticalLayoutContainer gridPanel = new VerticalLayoutContainer();
		gridPanel.add(grid, new VerticalLayoutData(1, 1));
		container.add(new FieldLabel(gridPanel, "Stocks"),new HtmlData(".stocks")); 
		
		TextButton cancelBtn = new TextButton("Cancel");
		TextButton submitBtn = new TextButton("Save");
		fpanel.setButtonAlign(BoxLayoutPack.CENTER);
		fpanel.addButton(submitBtn);
		fpanel.addButton(cancelBtn);
		List<FieldLabel> labels = FormPanelHelper.getFieldLabels(fpanel);
		for (FieldLabel lbl : labels) { lbl.setLabelAlign(LabelAlign.TOP);}

		vp.add(fpanel);
		this.add(vp);
		
		//Sell window
		sellWindow = new Window();
		sellQtyBtn = new TextButton("Sell");
		sellQtyBtn.setMinWidth(60);
		sellQtyBtn.addSelectHandler(new SellQtyHandler());
		sellQtyField = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
		FieldLabel sellField = new FieldLabel(sellQtyField, "Please enter quantity to sell");
		HorizontalLayoutContainer hc = new HorizontalLayoutContainer();
		hc.add(sellField); hc.add(sellQtyBtn);
		sellWindow.setWidget(hc);
		sellWindow.setMinWidth(350);
		sellWindow.setModal(true);
	}
	
	/**
	 * HTML table
	 * 
	 * @return
	 */
	private native String getTableMarkup() /*-{
		return [
				'<table width=100% cellpadding=20 cellspacing=20>',
				'<tr><td class=name width=50%></td><td class=type width=50%></td></tr>',
				'<tr><td class=stocks colspan=2></tr>', '</table>'
		].join("");
	}-*/;
	
	/**
	 * Sell button handler
	 * 
	 * @author Alexandra
	 *
	 */
	private class SellBtnHandler implements SelectHandler{

		@Override
		public void onSelect(SelectEvent event) {
			int row = event.getContext().getIndex();
			selectedStock = store.get(row);
			sellQtyField.getValidators().clear();
			sellQtyField.addValidator(new MaxNumberValidator<Integer>(selectedStock.getQuantity()));
			sellWindow.setHeadingText("Sell " + selectedStock.getType().getName());
			sellWindow.show();
		}
		
	}
	
	/**
	 * Sell stock quantity handler
	 * 
	 * @author Alexandra
	 *
	 */
	private class SellQtyHandler implements SelectHandler{

		@Override
		public void onSelect(SelectEvent event) {
			if(sellQtyField.isValid()){
				presenter.sell(selectedStock, sellQtyField.getValue());
				sellWindow.hide();
			}
			else {
				AlertMessageBox box = new AlertMessageBox("Sell error", "Not enough goods in stock.");
				box.show();
			}
			sellQtyField.clear();
		}
		
	}
	
	@Override
	public void setPresenter(LocationPresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setData(LocationDTO location) {
		clearData();
		this.location = location;
		this.store.addAll(location.getStocks());
		nameField.setValue(location.getName());
		typeField.setValue(location.getType().getDescription());
		setHeadingText(location.getName());

	}

	@Override
	public void clearData() {
		nameField.clear();
		typeField.clear();
		store.clear();

	}

}
