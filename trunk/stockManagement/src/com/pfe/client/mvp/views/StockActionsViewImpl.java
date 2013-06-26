package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.pfe.client.mvp.presenters.LocationPresenter;
import com.pfe.client.ui.ViewConstants;
import com.pfe.client.ui.properties.LocationProperties;
import com.pfe.client.ui.properties.StockProperties;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.StockDTO;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class StockActionsViewImpl extends Window implements StockActionsView {
	
	private static final StockProperties stockProps = GWT.create(StockProperties.class);
	private static final LocationProperties locationProps = GWT.create(LocationProperties.class);
	
	private LocationDTO location;
	private LocationPresenter presenter;
	private Grid<StockDTO> grid;
	private ListStore<StockDTO> stockStore;
	private StockDTO selectedStock;
	private VerticalLayoutContainer verticalContainer;
	
	private Window sellWindow;
	private NumberField<Integer> sellQtyField;

	private Window shipWindow;
	private NumberField<Integer> shipQtyField;
	private ListStore<LocationDTO> locationStore;
	private ComboBox<LocationDTO> locationCombo;
	
	public StockActionsViewImpl(){
		
		setModal(true);
		setMinHeight(430);
		setWidth(500);
		setHeight(500);
		
		//Stock grid
		int ratio = 1;
		ColumnConfig<StockDTO, String> typeCol = new ColumnConfig<StockDTO, String>(stockProps.type(), 3 * ratio, "Type");
		ColumnConfig<StockDTO, Integer> qtyCol = new ColumnConfig<StockDTO, Integer>(stockProps.quantity(), 3 * ratio, "Quantity");
		ColumnConfig<StockDTO, String> sellCol = new ColumnConfig<StockDTO, String>(stockProps.type(), 2 * ratio, "Sell");
		ColumnConfig<StockDTO, String> shipCol = new ColumnConfig<StockDTO, String>(stockProps.type(), 2 * ratio, "Ship");
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
		shipBtn.addSelectHandler(new ShipBtnHandler());
		shipCol.setCell(shipBtn);
		
		stockStore = new ListStore<StockDTO>(stockProps.key());
		grid = new Grid<StockDTO>(stockStore, cm);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.getView().setAutoFill(true);
		grid.setBorders(true);
		
		verticalContainer = new VerticalLayoutContainer();
		verticalContainer.setId("VERTICAL CONTAINER");
		PagingToolBar pagingToolBar = new PagingToolBar(ViewConstants.recordsPerPage);
		ToolBar toolbar = new ToolBar();
		verticalContainer.add(toolbar, new VerticalLayoutData(1, -1));
		verticalContainer.add(grid, new VerticalLayoutData(1, 1));
		verticalContainer.add(pagingToolBar, new VerticalLayoutData(1, ViewConstants.pagingBarHeight));
		
		Viewport viewport = new Viewport();
		viewport.add(verticalContainer);
		
		this.setWidget(viewport);
		
		//Sell window
		sellWindow = new Window();
		TextButton sellQtyBtn = new TextButton("Sell");
		sellQtyBtn.addSelectHandler(new SellQtyHandler());
		sellQtyField = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
		FieldLabel sellField = new FieldLabel(sellQtyField, "Quantity");
		VerticalLayoutContainer hc = new VerticalLayoutContainer();
		hc.add(sellField); hc.add(sellQtyBtn);
		sellWindow.setWidget(hc);
		sellWindow.setModal(true);
		sellWindow.setMinHeight(100);
		
		//Ship Window
		shipWindow = new Window();
		shipQtyField = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
		FieldLabel shipField = new FieldLabel(shipQtyField, "Quantity");
		TextButton shipQtyBtn = new TextButton("Ship");
		shipQtyBtn.addSelectHandler(new ShipQtyHandler());
		locationStore = new ListStore<LocationDTO>(locationProps.key());
		locationCombo = new ComboBox<LocationDTO>(locationStore, locationProps.nameLabel()); 
		FieldLabel locationField = new FieldLabel(locationCombo, "Destination");
		VerticalLayoutContainer vc = new VerticalLayoutContainer();
		vc.add(shipField); vc.add(locationField); vc.add(shipQtyBtn);
		shipWindow.setWidget(vc); 
		shipWindow.setModal(true);
		shipWindow.setMinHeight(120);
		
	}

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
			selectedStock = stockStore.get(row);
			sellQtyField.getValidators().clear();
			sellQtyField.addValidator(new MaxNumberValidator<Integer>(selectedStock.getQuantity()));
			sellWindow.setHeadingText("Sell " + selectedStock.getType().getName());
			sellWindow.show();
		}
		
	}
	
	/**
	 * Ship button handler
	 * 
	 * @author Alexandra
	 *
	 */
	private class ShipBtnHandler implements SelectHandler{

		@Override
		public void onSelect(SelectEvent event) {
			int row = event.getContext().getIndex();
			selectedStock = stockStore.get(row);
			shipQtyField.getValidators().clear();
			shipQtyField.addValidator(new MaxNumberValidator<Integer>(selectedStock.getQuantity()));
			shipWindow.setHeadingText("Ship " + selectedStock.getType().getName());
			presenter.getAll();
			shipWindow.show();
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
	
	/**
	 * Ship stock quantity handler
	 * 
	 * @author Alexandra
	 *
	 */
	private class ShipQtyHandler implements SelectHandler{

		@Override
		public void onSelect(SelectEvent event) {
			if(shipQtyField.isValid()){
				presenter.ship(selectedStock, shipQtyField.getValue(), locationCombo.getValue());
				shipWindow.hide();
			}
			else {
				AlertMessageBox box = new AlertMessageBox("Sell error", "Not enough goods in stock.");
				box.show();
			}
			shipQtyField.clear();
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
		this.stockStore.addAll(location.getStocks());
		setHeadingText(location.getName() + " - Stocks");

	}

	@Override
	public void clearData() {
		stockStore.clear();
		locationStore.clear();

	}

	@Override
	public void deleteData(StockDTO stock) {
		stockStore.remove(stock);

	}

	@Override
	public void updateData(StockDTO stock) {
		stockStore.update(stock);

	}

	@Override
	public void setLocations(List<LocationDTO> locations) {
		locationStore.clear();
		locationStore.addAll(locations);
		//Remove current location
		for(LocationDTO l : locations){
			if(l.getId().equals(location.getId())){
				locationStore.remove(l);
				break;
			}
		}
		
		locationCombo.setValue(null);
	}
	
}
