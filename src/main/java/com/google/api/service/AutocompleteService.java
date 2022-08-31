package com.google.api.service;

import com.google.api.model.autoComplete.AutoCompletionResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;

@RegisterRestClient(baseUri = "https://maps.googleapis.com/maps/api/place/autocomplete/json")
public interface AutocompleteService {
    @GET
    AutoCompletionResponse getAutoComplete(@HeaderParam("Authorization") String token,
                                           @QueryParam("input") String input,
                                           @QueryParam("language") String language,
                                           @QueryParam("radius") String radius,
                                           @QueryParam("sensor") String sensor,
                                           @QueryParam("key") String key);


}
