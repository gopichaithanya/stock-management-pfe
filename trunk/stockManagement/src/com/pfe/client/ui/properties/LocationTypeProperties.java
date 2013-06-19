package com.pfe.client.ui.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.dto.LocationTypeDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface LocationTypeProperties extends PropertyAccess<LocationTypeDTO> {


	@Path("id")
	ModelKeyProvider<LocationTypeDTO> key();
	
	@Path("description")
	LabelProvider<LocationTypeDTO> nameLabel();	
	
	@Path("description")
	ValueProvider<LocationTypeDTO, String> name();

}
