package com.google.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.model.autoComplete.AutoCompleteResponse;
import com.google.api.model.placeId.PlaceResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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



}