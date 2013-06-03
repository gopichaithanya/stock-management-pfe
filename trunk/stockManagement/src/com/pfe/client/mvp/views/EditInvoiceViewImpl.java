package com.pfe.client.mvp.views;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pfe.client.mvp.ClientFactory;
import com.pfe.client.mvp.presenters.InvoicePresenter;
import com.pfe.client.service.InvoiceServiceAsync;
import com.pfe.shared.dto.InvoiceDTO;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class EditInvoiceViewImpl extends Window implements EditInvoiceView {
	
	private Window parent;
	private InvoicePresenter presenter;
	private ClientFactory clientFactory;
	
	private InvoiceDTO invoice;
	
	public EditInvoiceViewImpl(Window parent, ClientFactory factory){
		this.parent = parent;
		this.clientFactory = factory;
		
		VerticalPanel vp = new VerticalPanel();
		FramedPanel fpanel = new FramedPanel();
		
		TextButton cancelBtn = new TextButton("Cancel");
		TextButton submitBtn = new TextButton("Save");
		submitBtn.addSelectHandler(new SubmitBtnHandler());
		cancelBtn.addSelectHandler(new CancelBtnHandler(this));

		fpanel.setButtonAlign(BoxLayoutPack.CENTER);
		fpanel.addButton(submitBtn);
		fpanel.addButton(cancelBtn);
	}

	
	/**
	 * Save updates
	 * 
	 * @author Alexandra
	 * 
	 */
	private class SubmitBtnHandler implements SelectHandler {

		@Override
		public void onSelect(SelectEvent event) {
			//if window parent is null, it means the window was opened from the invoice list
			if(parent == null){
				//TODO call presenter simple update method here
			}
			//if parent is edit supplier window
			else if (parent instanceof EditSupplierView){
				InvoiceServiceAsync rpcService = clientFactory.getInvoiceService();
				//call rpcservice here directly
			}
			
		}
	}
	
	/**
	 * Close window
	 * 
	 * @author Alexandra
	 * 
	 */
	private class CancelBtnHandler implements SelectHandler {

		private Window w;
		public CancelBtnHandler(Window w){
			this.w = w;
		}
		
		@Override
		public void onSelect(SelectEvent event) {
			w.hide();
		}
	}
	
	@Override
	public void setData(InvoiceDTO invoice) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPresenter(InvoicePresenter presenter) {
		this.presenter = presenter;
		
	}

}
