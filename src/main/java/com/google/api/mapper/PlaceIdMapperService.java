package com.google.api.mapper;

import com.google.api.model.placeId.PlaceIdResponse;
import com.google.api.model.placeId.PlaceResponse;
import org.springframework.stereotype.Component;

@Component
public class PlaceIdMapperService {

    public PlaceResponse map(PlaceIdResponse placeIdResponse) {
        PlaceResponse response = new PlaceResponse();
        response.setLatitude(placeIdResponse.getResult().getGeometry().getLocation().getLat());
        response.setLongitude(placeIdResponse.getResult().getGeometry().getLocation().getLng());

        return response;
    }

}
