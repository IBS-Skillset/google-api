package com.google.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.mapper.AutoCompleteMapperService;
import com.google.api.mapper.PlaceIdMapperService;
import com.google.api.model.autoComplete.AutoCompleteResponse;
import com.google.api.model.autoComplete.AutoCompletionResponse;
import com.google.api.model.placeId.PlaceIdResponse;
import com.google.api.model.placeId.PlaceResponse;
import com.google.api.service.CacheService;
import com.google.api.service.GoogleApiRestService;
import io.quarkus.logging.Log;
import org.apache.commons.text.WordUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Objects;

import static com.google.api.util.Constants.*;

@RestController
@RequestMapping("/googleApi")
public class GoogleAPiController {

    @Inject
    @RestClient
    GoogleApiRestService autocompleteService;
    @Autowired
    AutoCompleteMapperService autoCompleteMapperService;
    @Autowired
    PlaceIdMapperService placeIdMapperService;
    @Inject
    CacheService cacheService;

    @ConfigProperty(name = "google.token" )
    String token;

    @ConfigProperty(name = "google.key")
    String key;

    @GetMapping("/autoComplete/")
    public AutoCompleteResponse autoComplete(@RequestParam String input) {
        AutoCompleteResponse response = cacheService.autoCompleteResponseFromCache(WordUtils.capitalize(input));
        if (!cacheService.isCacheStatus() || Objects.isNull(response)) {
            AutoCompletionResponse autoCompletionResponse = autocompleteService.getPlaceAutoComplete(token, input, LANGUAGE, RADIUS, SENSOR, key);
            response = autoCompleteMapperService.map(autoCompletionResponse);
            if (cacheService.isCacheStatus()) {
                cacheService.cacheAutoCompleteResponse(response);
            }
            Log.info("Google API response for autocomplete" + autoCompletionResponse);
            Log.info(response);
        } else {
            Log.info("From cache" +response);
        }
        return response;
    }

    @GetMapping("/placeId/")
    public PlaceResponse getLatLongByPlaceId(@RequestParam String placeId) throws JsonProcessingException {
        PlaceResponse response = cacheService.placeResponseFromCache(placeId);
        if (!cacheService.isCacheStatus() || Objects.isNull(response)){
            PlaceIdResponse placeIdResponse =   autocompleteService.getPlaceDetails(token, placeId, LANGUAGE, SENSOR, key);
            response = placeIdMapperService.map(placeIdResponse);
            if (cacheService.isCacheStatus()) {
                cacheService.cachePlaceResponse(placeId, response);
            }
            Log.info(response);
        } else {
            Log.info("From cache" + response);
        }
        return response;
    }

}
