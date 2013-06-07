package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.ShipmentDTO;

public interface ShipmentServiceAsync {

	void delete(ShipmentDTO shipment, AsyncCallback<Void> callback);

	void deleteList(List<ShipmentDTO> shipments, AsyncCallback<Void> callback);

}
