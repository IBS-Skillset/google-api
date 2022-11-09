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
    AutoCompletionResponse getPlaceAutoComplete(@HeaderParam("Authorization") String token,
                                                @QueryParam("input") String input,
                                                @QueryParam("language") String language,
                                                @QueryParam("radius") String radius,
                                                @QueryParam("sensor") String sensor,
                                                @QueryParam("key") String key);

    @GET
    @Path(Constants.GOOGLE_API_DETAILS_URL)
    PlaceIdResponse getPlaceDetails(@HeaderParam("Authorization") String token,
                                    @QueryParam(Constants.PLACE_ID) String placeId,
                                    @QueryParam("language") String language,
                                    @QueryParam("sensor") String sensor,
                                    @QueryParam("key") String key);
}
