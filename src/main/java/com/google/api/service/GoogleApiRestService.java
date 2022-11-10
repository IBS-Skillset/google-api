package com.google.api.service;

import com.google.api.model.autoComplete.AutoCompletionResponse;
import com.google.api.model.placeId.PlaceIdResponse;
import com.google.api.util.Constants;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@RegisterRestClient(baseUri = Constants.GOOGLE_API_BASE_URL)
public interface GoogleApiRestService {
    @GET
    @Path(Constants.GOOGLE_API_AUTOCOMPLETE_URL)
    AutoCompletionResponse getPlaceAutoComplete(@HeaderParam(Constants.AUTHORIZATION) String token,
                                                @QueryParam(Constants.INPUT) String input,
                                                @QueryParam(Constants.HEADER_LANGUAGE) String language,
                                                @QueryParam(Constants.HEADER_RADIUS) String radius,
                                                @QueryParam(Constants.HEADER_SENSOR) String sensor,
                                                @QueryParam(Constants.KEY) String key);

    @GET
    @Path(Constants.GOOGLE_API_DETAILS_URL)
    PlaceIdResponse getPlaceDetails(@HeaderParam(Constants.AUTHORIZATION) String token,
                                    @QueryParam(Constants.PLACE_ID) String placeId,
                                    @QueryParam(Constants.HEADER_LANGUAGE) String language,
                                    @QueryParam(Constants.HEADER_SENSOR) String sensor,
                                    @QueryParam(Constants.KEY) String key);
}
