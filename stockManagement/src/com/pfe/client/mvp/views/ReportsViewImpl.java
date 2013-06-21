package com.pfe.client.mvp.views;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.client.mvp.presenters.ReportsPresenter;
import com.pfe.client.ui.properties.ProductTypeProperties;
import com.pfe.client.ui.properties.StockProperties;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.StockDTO;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class ReportsViewImpl implements ReportsView {
	private static final ProductTypeProperties typeProps = GWT.create(ProductTypeProperties.class);
	private static final StockProperties stockProps = GWT.create(StockProperties.class);

	private ReportsPresenter presenter;
	private VerticalLayoutContainer verticalCon;
	
	private ListStore<ProductTypeDTO> productStore;
	private ComboBox<ProductTypeDTO> productCombo;
	private TextButton findStocksBtn;
	
	private ListStore<StockDTO> stockStore;
	private Grid<StockDTO> stockGrid;
	
	public ReportsViewImpl(){
		verticalCon = new VerticalLayoutContainer();
		productStore = new ListStore<ProductTypeDTO>(typeProps.key());
		productCombo = new ComboBox<ProductTypeDTO>(productStore, typeProps.nameLabel());
		productCombo.setEmptyText("Choose a product");
		findStocksBtn = new TextButton("Find stocks");
		findStocksBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				ProductTypeDTO selected = productCombo.getValue();
				if(selected != null){
					presenter.getStocks(productCombo.getValue());
				}
			}
		});
		
		TextButton sampleBtn = new TextButton("Sample btn");	
		ToolBar toolbar = new ToolBar();
		toolbar.add(findStocksBtn);
		toolbar.add(productCombo);
		toolbar.add(new SeparatorToolItem());
		toolbar.add(sampleBtn);
		verticalCon.add(toolbar, new VerticalLayoutData(1, -1));
		
		stockStore = new ListStore<StockDTO>(stockProps.key());
		// Column configuration
		int ratio = 1;
		ColumnConfig<StockDTO, String> locationCol = new ColumnConfig<StockDTO, String>(stockProps.location(),ratio, "Location");
		ColumnConfig<StockDTO, Integer> qtyCol = new ColumnConfig<StockDTO, Integer>(stockProps.quantity(), ratio, "Quantity");
		List<ColumnConfig<StockDTO, ?>> columnConfigList = new ArrayList<ColumnConfig<StockDTO, ?>>();
		columnConfigList.add(locationCol);
		columnConfigList.add(qtyCol);
		ColumnModel<StockDTO> cm = new ColumnModel<StockDTO>(columnConfigList);

		// Grid
		stockGrid = new Grid<StockDTO>(stockStore, cm);
		stockGrid.getView().setStripeRows(true);
		stockGrid.getView().setColumnLines(true);
		stockGrid.setBorders(false);
		stockGrid.getView().setAutoFill(true);
		
	}
	
	@Override
	public Widget asWidget() {
		return verticalCon;
	}

	@Override
	public void setPresenter(ReportsPresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void setProductTypes(List<ProductTypeDTO> productTypes) {
		productStore.clear();
		productStore.addAll(productTypes);
		
	}

	@Override
	public void setStocks(List<StockDTO> stocks) {
		removeGrids();
		if(stocks.size() == 0){
			AlertMessageBox box = new AlertMessageBox("Alert", "No stocks found");
			box.show();	
		} else{
			stockStore.clear();
			stockStore.addAll(stocks);
			verticalCon.add(stockGrid, new VerticalLayoutData(1, 1));
		}
		verticalCon.forceLayout();
	}

	/**
	 * Removes current grid before displaying other reports
	 * 
	 */
	private void removeGrids(){
		//Remove stock grid
		int index = verticalCon.getWidgetIndex(stockGrid);
		if(index != -1){
			verticalCon.remove(stockGrid);
		}
		//Remove other grids
	}
}
  