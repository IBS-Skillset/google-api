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
import com.google.api.service.VaultService;
import io.quarkus.logging.Log;
import io.quarkus.vault.VaultKVSecretEngine;
import org.apache.commons.text.WordUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Map;
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
    @Inject
    VaultKVSecretEngine kvSecretEngine;

    @GetMapping("/autoComplete/")
    public AutoCompleteResponse autoComplete(@RequestParam String input) throws JsonProcessingException {
        VaultService vaultService = VaultService.INSTANCE.getInstance();
        populateValuesFromVault(vaultService);
        AutoCompleteResponse response = cacheService.autoCompleteResponseFromCache(WordUtils.capitalize(input));
        if (Objects.isNull(response)) {
            AutoCompletionResponse autoCompletionResponse = autocompleteService.getPlaceAutoComplete(vaultService.getToken(), input, LANGUAGE, RADIUS, SENSOR, vaultService.getKey());
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
            VaultService vaultService = VaultService.INSTANCE.getInstance();
            populateValuesFromVault(vaultService);
            PlaceIdResponse placeIdResponse =   autocompleteService.getPlaceDetails(vaultService.getToken(), placeId, LANGUAGE, SENSOR, vaultService.getKey());
            response = placeIdMapperService.map(placeIdResponse);
            cacheService.cachePlaceResponse(placeId,response);
            Log.info(response);
        } else {
            Log.info("From cache" + response);
        }
        return response;
    }

    private void populateValuesFromVault(VaultService vaultService) {
        if(null== vaultService.getKey() || null== vaultService.getToken()) {
            Map<String, String> vaultSecretMap = kvSecretEngine.readSecret(vaultPath);
            vaultService.setToken(vaultSecretMap.get(TOKEN));
            vaultService.setKey(vaultSecretMap.get(KEY));
        }
    }
}
