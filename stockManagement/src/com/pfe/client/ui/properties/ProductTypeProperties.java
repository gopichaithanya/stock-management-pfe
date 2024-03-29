package com.pfe.client.ui.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.pfe.shared.dto.ProductTypeDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ProductTypeProperties extends PropertyAccess<ProductTypeDTO> {

	@Path("id")
	ModelKeyProvider<ProductTypeDTO> key();
	
	@Path("name")
	LabelProvider<ProductTypeDTO> nameLabel();	

	@Path("name")
	ValueProvider<ProductTypeDTO, String> name();
	

	@Path("description")
	ValueProvider<ProductTypeDTO, String> description();
}
