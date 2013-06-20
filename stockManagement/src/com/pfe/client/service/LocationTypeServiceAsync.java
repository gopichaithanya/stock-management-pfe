package com.pfe.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pfe.shared.dto.LocationTypeDTO;

public interface LocationTypeServiceAsync {

	void getAll(AsyncCallback<List<LocationTypeDTO>> callback);

}
