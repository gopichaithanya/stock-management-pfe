package com.pfe.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.pfe.client.mvp.AppActivityMapper;
import com.pfe.client.mvp.AppPlaceHistoryMapper;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.ClientFactoryImpl;
import com.pfe.client.mvp.places.ProductTypesPlace;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class StockManagement implements EntryPoint {
	
	private ToolBar toolBar;

	@SuppressWarnings("deprecation")
	@Override
	public void onModuleLoad() {

		System.out.println("test");

		ClientFactory clientFactory = new ClientFactoryImpl();
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper,
				eventBus);
		
		
		// This is the panel that will contain the different views
		SimpleContainer centerPanel = new SimpleContainer();
		activityManager.setDisplay(centerPanel);
		
		// TODO remove this
		ProductTypesPlace testPlace = new ProductTypesPlace();

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		// register controller with place history handler
		historyHandler.register(placeController, eventBus, testPlace);
		// handle whatever place arrives when application starts
		historyHandler.handleCurrentHistory();

		// create the frame of the window
		Viewport viewport = new Viewport();
		buildToolbar();
		
		VerticalLayoutContainer con = new VerticalLayoutContainer();
		con.add(toolBar, new VerticalLayoutData(1, -1));
	    con.add(centerPanel,  new VerticalLayoutData(1, 1, new Margins(10)));

	    viewport.add(con);
		RootPanel.get().add(viewport.asWidget());

	}
	
	/**
	 * Builds the menu
	 * 
	 */
	public void buildToolbar() {
		
		SelectionHandler<Item> handler = new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				//MenuItem item = (MenuItem) event.getSelectedItem();
				//String text = item.getText();
//				if("Stores".equals(text)){
//					goTo(new StoresListPlace());
//				} else if("Warehouses".equals(text)){
//					
//				} else if("Suppliers".equals(text)){
//					goTo(new SuppliersListPlace());
//				} else if("Invoice".equals(text)){
//					
//				}
			}
		};
		
		toolBar = new ToolBar();
		//toolBar.setLayoutData(new VerticalLayoutData(1, -1));
		toolBar.setSpacing(5);
		toolBar.setPadding(new Padding(10));
		toolBar.setHeight(40);

		Menu storageMenu = new Menu();
		storageMenu.addSelectionHandler(handler);
		TextButton storageBtn = new TextButton("Storage");
		storageBtn.setMenu(storageMenu);
		MenuItem storeItem = new MenuItem("Stores");
		MenuItem warehouseItem = new MenuItem("Warehouses");
		storageMenu.add(storeItem);
		storageMenu.add(warehouseItem);
		
		Menu supplyMenu = new Menu();
		supplyMenu.addSelectionHandler(handler);
		TextButton supplyBtn = new TextButton("Supply");
		supplyBtn.setMenu(supplyMenu);
		MenuItem suppliersItem = new MenuItem("Suppliers");
		MenuItem invoiceItem = new MenuItem("Invoice");
		supplyMenu.add(suppliersItem);
		supplyMenu.add(invoiceItem);

		toolBar.add(storageBtn);
		toolBar.add(supplyBtn);
	}

}
