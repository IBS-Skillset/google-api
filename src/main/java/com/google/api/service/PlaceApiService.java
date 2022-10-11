package com.google.api.service;


import com.google.api.model.placeId.PlaceIdResponse;
import com.google.api.util.APIConstants;
import io.quarkus.logging.Log;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.ClientBuilder;

@Service
public class PlaceApiService {

    public PlaceIdResponse getLatitudeLongitude(String placeId) {
        ResteasyClient resteasyClient = (ResteasyClient) ClientBuilder.newClient();
        ResteasyWebTarget target = resteasyClient.target("https://maps.googleapis.com/maps/api/place/details/json");
        target = target.queryParam("placeid", placeId).queryParam("language", APIConstants.LANGUAGE).
                queryParam("sensor", APIConstants.SENSOR).
                queryParam("key", APIConstants.KEY);
        PlaceIdResponse response = target.request().get(PlaceIdResponse.class);
        Log.info(target.getUri());
        Log.info(response);

        return response;
    }

}
