package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.ShipmentDTO;

public interface ShipmentServiceAsync {

	void delete(List<ShipmentDTO> shipments, AsyncCallback<Void> callback);


}
