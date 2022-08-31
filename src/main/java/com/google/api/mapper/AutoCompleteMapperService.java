package com.google.api.mapper;

import com.google.api.model.autoComplete.AutoCompleteResponse;
import com.google.api.model.autoComplete.AutoCompletionResponse;
import com.google.api.model.autoComplete.Place;
import com.google.api.model.autoComplete.Predictions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AutoCompleteMapperService {
    public AutoCompleteResponse map(AutoCompletionResponse autoCompletionResponse) {
        AutoCompleteResponse response = new AutoCompleteResponse();
        List<Place> placeList = new ArrayList<>();
        placeList = autoCompletionResponse.getPredictions().stream()
                .map(predictions -> mapPlace(predictions))
                .collect(Collectors.toList());
        response.setPlace(placeList);
        return response;
    }
    private Place mapPlace(Predictions prediction) {
        Place place = new Place();
        place.setPlaceId(prediction.getPlaceId());
        if (prediction.getDescription().split(",").length >= 2) {
            String[] description = prediction.getDescription().split(",");
            place.setDescription(description[0] + "," + description[1]);
        } else {
            place.setDescription(prediction.getDescription().split(",")[0]);
        }
        return place;
    }
}
