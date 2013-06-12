package com.pfe.client.ui.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.dto.LocationDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface LocationProperties extends PropertyAccess<LocationDTO> {
	
	@Path("id")
	ModelKeyProvider<LocationDTO> key();
	
	@Path("name")
	LabelProvider<LocationDTO> nameLabel();	
	
	@Path("name")
	ValueProvider<LocationDTO, String> name();

	@Path("type.description")
	ValueProvider<LocationDTO, String> type();

}
