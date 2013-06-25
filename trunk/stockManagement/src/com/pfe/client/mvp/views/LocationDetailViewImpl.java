package com.pfe.client.mvp.views;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.pfe.shared.dto.LocationDTO;
import com.pfe.shared.dto.StockDTO;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class LocationDetailViewImpl implements LocationDetailView {
	
	private LocationDTO location;
	private ContentPanel stockPanel;
	private VerticalLayoutContainer verticalCon;
	private VerticalLayoutData layoutData;

	public LocationDetailViewImpl(){
		
		AccordionLayoutAppearance appearance = GWT.<AccordionLayoutAppearance> create(AccordionLayoutAppearance.class);
		// details : stocks tab
		stockPanel = new ContentPanel(appearance);
		stockPanel.setBodyStyleName("rawText");
		stockPanel.setHeadingText("Stocks");
		verticalCon = new VerticalLayoutContainer();
		layoutData = new VerticalLayoutData();
		layoutData.setMargins(new Margins(10, 5, 10, 5));
		stockPanel.add(verticalCon);
	}
	
	@Override
	public Widget asWidget() {
		return stockPanel;
	}

	@Override
	public void clearData() {
		verticalCon.clear();

	}

	@Override
	public void setData(LocationDTO data) {
		this.location = data;
		List<StockDTO> stocks = location.getStocks();
		
		for(StockDTO stock : stocks){
			Label label = new Label();
			label.setText(stock.getType().getName() + " : " + stock.getQuantity() + " items");
			verticalCon.add(label, layoutData);
		}
	}

}
