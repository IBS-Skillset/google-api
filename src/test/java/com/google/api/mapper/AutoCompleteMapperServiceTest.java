package com.google.api.mapper;

import com.google.api.model.autoComplete.AutoCompleteResponse;
import com.google.api.model.autoComplete.AutoCompletionResponse;
import com.google.api.model.autoComplete.Predictions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AutoCompleteMapperServiceTest {
    private static AutoCompleteMapperService autoCompleteMapperService;
    @BeforeAll
    public static void setUP() {
        autoCompleteMapperService = new AutoCompleteMapperService();
    }
    @AfterAll
    public static void tearDown() {
       autoCompleteMapperService = null;
    }
    @Test
    public void testMap() {
        AutoCompletionResponse response = getAutoResponse();
        AutoCompleteResponse autoCompleteResponse =  autoCompleteMapperService.map(response);
        assertEquals(autoCompleteResponse.getPlace().get(0).getDescription(),"New York");
        assertEquals(autoCompleteResponse.getPlace().get(1).getDescription(),"New york,USA");
    }
    private AutoCompletionResponse getAutoResponse() {
        AutoCompletionResponse response = new AutoCompletionResponse();
        List<Predictions> predictionList = new ArrayList<>();
        Predictions prediction = new Predictions();
        prediction.setDescription("New York");
        prediction.setPlaceId("gvghvh");
        predictionList.add(prediction);
        Predictions prediction1 = new Predictions();
        prediction1.setPlaceId("vgvyhvujh");
        prediction1.setDescription("New york,USA");
        predictionList.add(prediction1);
        response.setPredictions(predictionList);
        return response;
    }
}