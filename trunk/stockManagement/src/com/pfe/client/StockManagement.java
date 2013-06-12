package com.pfe.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.pfe.client.mvp.AppActivityMapper;
import com.pfe.client.mvp.AppPlaceHistoryMapper;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.ClientFactoryImpl;
import com.pfe.client.mvp.DetailsActivityMapper;
import com.pfe.client.mvp.places.InvoiceListPlace;
import com.pfe.client.mvp.places.LocationListPlace;
import com.pfe.client.mvp.places.ProductTypeListPlace;
import com.pfe.client.mvp.places.SupplierListPlace;
import com.pfe.client.mvp.places.WelcomePlace;
import com.pfe.client.ui.CustomContentPanel;
import com.pfe.client.ui.images.ImageResources;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class StockManagement implements EntryPoint {

	private ToolBar toolBar;
	private ClientFactory clientFactory;

	@SuppressWarnings("deprecation")
	@Override
	public void onModuleLoad() {

		clientFactory = new ClientFactoryImpl();
		
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		BorderLayoutContainer borderLayoutContainer = new BorderLayoutContainer();
		ContentPanel east = new ContentPanel();
		east.setBorders(true);
		east.setHeadingHtml("Details");
		CustomContentPanel center = new CustomContentPanel();
		center.setBorders(true);
		center.setHeadingHtml("Records");

		// center data
		MarginData centerData = new MarginData();
		centerData.setMargins(new Margins(0, 0, 0, 0));

		// west data
		BorderLayoutData eastData = new BorderLayoutData(400);
		eastData.setMargins(new Margins(0, 0, 0, 5));
		eastData.setSplit(true);
		eastData.setCollapsible(true);

		borderLayoutContainer.setEastWidget(east, eastData);
		borderLayoutContainer.setCenterWidget(center, centerData);

		// Start ActivityManager for the central widget with our ActivityMapper
		ActivityMapper mainActivityMapper = new AppActivityMapper(clientFactory);
		ActivityManager mainActivityManager = new ActivityManager(
				mainActivityMapper, eventBus);

		// Center panel
		mainActivityManager.setDisplay(center);

		// Start ActivityManager for the details panel
		DetailsActivityMapper detailsActivityMapper = new DetailsActivityMapper(
				clientFactory);
		ActivityManager detailsActivityManager = new ActivityManager(
				detailsActivityMapper, eventBus);
		detailsActivityManager.setDisplay(east);

		// Start with welcome place
		WelcomePlace welcomePlace = new WelcomePlace();

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		// register controller with place history handler
		historyHandler.register(placeController, eventBus, welcomePlace);
		// handle whatever place arrives when application starts
		historyHandler.handleCurrentHistory();

		// create the frame of the window
		Viewport viewport = new Viewport();
		buildToolbar();

		VerticalLayoutContainer con = new VerticalLayoutContainer();
		con.add(toolBar, new VerticalLayoutData(1, 45));
		con.add(borderLayoutContainer, new VerticalLayoutData(1, 1,
				new Margins(10)));

		viewport.add(con);
		RootPanel.get().add(viewport.asWidget());

	}

	/**
	 * Builds the menu
	 * 
	 */
	public void buildToolbar() {
		
		toolBar = new ToolBar();
		toolBar.setSpacing(5);
		toolBar.setPadding(new Padding(10));
		toolBar.setHeight(40);

		TextButton productBtn = new TextButton("Products",ImageResources.INSTANCE.addProductIcon());
		productBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				goTo(new ProductTypeListPlace());
				
			}
		});

		Menu storageMenu = new Menu();
		storageMenu.addSelectionHandler(new SelectionHandler<Item>() {

			@Override
			public void onSelection(SelectionEvent<Item> event) {
				MenuItem item = (MenuItem) event.getSelectedItem();
				String text = item.getText();
				if ("Location Types".equals(text)) {
					//goTo(new LocationTypeListPlace());
				} else if ("Locations".equals(text)) {
					goTo(new LocationListPlace());
				}
				
			}
		});
		
		TextButton storageBtn = new TextButton("Storage",ImageResources.INSTANCE.addStoreItcon());
		storageBtn.setMenu(storageMenu);
		MenuItem lTypeItem = new MenuItem("Location Types");
		MenuItem locationItem = new MenuItem("Locations");
		storageMenu.add(lTypeItem);
		storageMenu.add(locationItem);

		TextButton supplierBtn = new TextButton("Suppliers",ImageResources.INSTANCE.addSupplierIcon());
		supplierBtn.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				goTo(new SupplierListPlace());
			}
			
		});
		TextButton invoiceBtn = new TextButton("Invoices",ImageResources.INSTANCE.addInvoiceIcon());
		invoiceBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				goTo(new InvoiceListPlace());
				
			}
		});
		TextButton reportBtn = new TextButton("Reports",ImageResources.INSTANCE.addInvoiceIcon());
		toolBar.add(productBtn);
		toolBar.add(storageBtn);
		toolBar.add(supplierBtn);
		toolBar.add(invoiceBtn);
		toolBar.add(reportBtn);
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
