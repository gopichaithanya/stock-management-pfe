package com.pfe.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.pfe.client.mvp.places.ProductTypeListPlace;

/**
 * Maps a URL to a place and encodes/decodes data into tokens in that URL. Adds
 * places to the history manager
 * 
 * @author Alexandra
 * 
 */
@WithTokenizers({ ProductTypeListPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
