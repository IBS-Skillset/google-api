package com.google.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.model.autoComplete.AutoCompleteResponse;
import com.google.api.model.autoComplete.Place;
import com.google.api.model.placeId.PlaceResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.atLeast;

public class CacheServiceTest {

    private static CacheService cacheService;

    @BeforeAll
    public static void setCacheService() {
        cacheService = new CacheService("localhost",6379);
    }

    @AfterAll
    public static void tearDown() {
        cacheService = null;
    }

    @Test
    public void autoCompleteResponseFromCacheTest() {
        AutoCompleteResponse response = cacheService.autoCompleteResponseFromCache("123");
        assertEquals(response,null);
    }

    @Test
    public void placeResponseFromCacheTest() throws JsonProcessingException {
        PlaceResponse response = cacheService.placeResponseFromCache("123");
        assertEquals(response,null);
    }

    @Test
    public void cacheAutoCompleteResponseTest() {
        CacheService cacheServiceMock = mock(CacheService.class);
        AutoCompleteResponse autoCompleteResponse = mock(AutoCompleteResponse.class);
        Place place = new Place();
        place.setPlaceId("xxxaaa");
        place.setDescription("Amsterdam Zuid");
        List<Place> placeList = new ArrayList<>();
        placeList.add(place);
        autoCompleteResponse.setPlace(placeList);
        doNothing().when(cacheServiceMock).cacheAutoCompleteResponse(autoCompleteResponse);
        cacheServiceMock.cacheAutoCompleteResponse(autoCompleteResponse);
        verify(cacheServiceMock,atLeast(1)).cacheAutoCompleteResponse(autoCompleteResponse);
    }

    @Test
    public void cachePlaceResponseTest() throws JsonProcessingException {
        CacheService cacheServiceMock = mock(CacheService.class);
        PlaceResponse placeResponse = mock(PlaceResponse.class);
        placeResponse.setLatitude("2.4545");
        placeResponse.setLongitude("3.4565");
        doNothing().when(cacheServiceMock).cachePlaceResponse("dd2321srrty",placeResponse);
        cacheServiceMock.cachePlaceResponse("dd2321srrty",placeResponse);
        verify(cacheServiceMock,atLeast(1)).cachePlaceResponse("dd2321srrty",placeResponse);
    }
}