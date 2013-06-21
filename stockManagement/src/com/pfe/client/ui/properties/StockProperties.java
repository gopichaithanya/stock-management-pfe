package com.pfe.client.ui.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.dto.StockDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface StockProperties extends PropertyAccess<StockDTO> {
	
	@Path("id")
	ModelKeyProvider<StockDTO> key();
	
	@Path("id")
	ValueProvider<StockDTO, Long> id();

	@Path("quantity")
	ValueProvider<StockDTO, Integer> quantity();
	
	@Path("type.name")
	ValueProvider<StockDTO, String> type();
	
	@Path("location.name")
	ValueProvider<StockDTO, String> location();

}
