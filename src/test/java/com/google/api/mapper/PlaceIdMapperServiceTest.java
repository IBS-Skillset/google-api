package com.google.api.mapper;

import com.google.api.model.placeId.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceIdMapperServiceTest {
    private static PlaceIdMapperService placeIdMapperService;

    @BeforeAll
    public static void setUP() {
        placeIdMapperService = new PlaceIdMapperService();
    }

    @AfterAll
    public static void tearDown() {
        placeIdMapperService = null;
    }


    @Test
    public void testMap() {
        PlaceIdResponse response = getPlaceIdResponse();
        PlaceResponse placeResponse = placeIdMapperService.map(response);
        assertEquals(placeResponse.getLongitude(), "1.345354");
        assertEquals(placeResponse.getLatitude(), "1.484553");

    }

    private PlaceIdResponse getPlaceIdResponse() {
        PlaceIdResponse response = new PlaceIdResponse();
        Result result = new Result();
        Geometry geometry = new Geometry();
        Location location = new Location();
        location.setLng("1.345354");
        location.setLat("1.484553");
        geometry.setLocation(location);
        result.setGeometry(geometry);
        response.setResult(result);
        return response;


    }
}
