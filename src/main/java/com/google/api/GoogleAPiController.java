package com.google.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.api.mapper.AutoCompleteMapperService;
import com.google.api.model.autoComplete.AutoCompleteResponse;
import com.google.api.model.autoComplete.AutoCompletionResponse;
import com.google.api.service.AutocompleteService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.json.JsonString;
import javax.json.JsonWriter;

import static com.google.api.util.APIConstants.*;


@RestController
@RequestMapping("/googleApi")
public class GoogleAPiController {
    private static Logger logger = LoggerFactory.getLogger(GoogleAPiController.class);
    @Inject
    @RestClient
    AutocompleteService autocompleteService;
    @Autowired
    AutoCompleteMapperService autoCompleteMapperService;

    @GetMapping("/autoComplete/")
    public AutoCompleteResponse autoComplete(@RequestParam String input) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        AutoCompletionResponse autoCompletionResponse = autocompleteService.getAutoComplete(TOKEN,input, LANGUAGE, RADIUS, SENSOR, KEY);
        AutoCompleteResponse response = autoCompleteMapperService.map(autoCompletionResponse);
        logger.info("Google API response for autocomplete"+mapper.writeValueAsString(autoCompletionResponse));
        logger.info(mapper.writeValueAsString(response));
        return response;
    }
}
