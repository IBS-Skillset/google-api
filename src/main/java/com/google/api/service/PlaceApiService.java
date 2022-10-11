package com.google.api.service;


import com.google.api.model.placeId.PlaceIdResponse;
import com.google.api.util.APIConstants;
import io.quarkus.logging.Log;
import io.quarkus.vault.VaultKVSecretEngine;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import java.util.Map;

@Service
public class PlaceApiService {

    @Inject
    VaultKVSecretEngine kvSecretEngine;

    public PlaceIdResponse getLatitudeLongitude(String placeId) {
        Map<String, String> vaultSecretMap = kvSecretEngine.readSecret("google-api" + "/config");
        ResteasyClient resteasyClient = (ResteasyClient) ClientBuilder.newClient();
        ResteasyWebTarget target = resteasyClient.target("https://maps.googleapis.com/maps/api/place/details/json");
        target = target.queryParam("placeid", placeId).queryParam("language", APIConstants.LANGUAGE).
                queryParam("sensor", APIConstants.SENSOR).
                queryParam("key", vaultSecretMap.get("key"));
        PlaceIdResponse response = target.request().get(PlaceIdResponse.class);
        Log.info(target.getUri());
        Log.info(response);

        return response;
    }

}
