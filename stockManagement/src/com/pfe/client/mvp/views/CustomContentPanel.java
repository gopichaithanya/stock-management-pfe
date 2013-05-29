package com.pfe.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;

public class CustomContentPanel extends ContentPanel {
	
	public CustomContentPanel(){
		super();
	}

	  @Override
	  public void setWidget(IsWidget w) {
	    setWidget(asWidgetOrNull(w));
	    doLayout();
	  }
	  
	  @Override
	  public void add(Widget child) {
	    setWidget(child);
	    doLayout();
	  }

}
