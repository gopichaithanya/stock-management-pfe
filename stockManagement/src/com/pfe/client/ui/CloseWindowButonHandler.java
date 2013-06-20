package com.pfe.client.ui;

import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * This handler is added to buttons that close windows
 * 
 * @author Alexandra
 *
 */
public class CloseWindowButonHandler implements SelectHandler {
	
	private Window w;
	public CloseWindowButonHandler(Window w){
		this.w = w;
	}
	
	@Override
	public void onSelect(SelectEvent event) {
		w.hide();
	}

}
