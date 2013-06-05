package com.pfe.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.pfe.client.mvp.places.InvoiceListPlace;
import com.pfe.client.mvp.places.ProductTypeListPlace;
import com.pfe.client.mvp.places.SupplierListPlace;

/**
 * Maps a URL to a place and encodes/decodes data into tokens in that URL. Adds
 * places to the history manager
 * 
 * @author Alexandra
 * 
 */
@WithTokenizers({ ProductTypeListPlace.Tokenizer.class,
		SupplierListPlace.Tokenizer.class, InvoiceListPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
