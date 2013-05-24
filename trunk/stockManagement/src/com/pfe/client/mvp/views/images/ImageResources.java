package com.pfe.client.mvp.views.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageResources extends ClientBundle {

	  public ImageResources INSTANCE = GWT.create(ImageResources.class);

	  @Source("btn_add.png")
	  ImageResource addCreateIcon();
	  
	  @Source("btn_delete.png")
	  ImageResource addDeleteIcon();
	  
	  @Source("btn_edit.png")
	  ImageResource addEditItcon();

}
