package com.pfe.client.mvp.views.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageResources extends ClientBundle {

	  public ImageResources INSTANCE = GWT.create(ImageResources.class);

	  @Source("create.png")
	  ImageResource addCreateIcon();

}
