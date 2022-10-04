package com.google.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.mapper.AutoCompleteMapperService;
import com.google.api.mapper.PlaceIdMapperService;
import com.google.api.model.autoComplete.AutoCompleteResponse;
import com.google.api.model.autoComplete.AutoCompletionResponse;
import com.google.api.model.placeId.PlaceIdResponse;
import com.google.api.model.placeId.PlaceResponse;
import com.google.api.service.AutocompleteService;
import com.google.api.service.CacheService;
import com.google.api.service.PlaceApiService;
import io.quarkus.logging.Log;
import org.apache.commons.text.WordUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Objects;

import static com.google.api.util.APIConstants.*;


@RestController
@RequestMapping("/googleApi")
public class GoogleAPiController {

    @Inject
    @RestClient
    AutocompleteService autocompleteService;
    @Autowired
    AutoCompleteMapperService autoCompleteMapperService;
    @Autowired
    PlaceApiService placeApiService;
    @Autowired
    PlaceIdMapperService placeIdMapperService;
    @Inject
    CacheService cacheService;


    @GetMapping("/autoComplete/")
    public AutoCompleteResponse autoComplete(@RequestParam String input) throws JsonProcessingException {
        AutoCompleteResponse response = cacheService.autoCompleteResponseFromCache(WordUtils.capitalize(input));
        if (Objects.isNull(response)) {
            AutoCompletionResponse autoCompletionResponse = autocompleteService.getAutoComplete(TOKEN, input, LANGUAGE, RADIUS, SENSOR, KEY);
            response = autoCompleteMapperService.map(autoCompletionResponse);
            cacheService.cacheAutoCompleteResponse(response);
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
        if (Objects.isNull(response)){
            PlaceIdResponse placeIdResponse = placeApiService.getLatitudeLongitude(placeId);
            response = placeIdMapperService.map(placeIdResponse);
            cacheService.cachePlaceResponse(placeId,response);
            Log.info(response);
        } else {
            Log.info("From cache" + response);
        }
        return response;
    }

}
