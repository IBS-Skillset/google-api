package com.google.api.model.placeId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceIdResponse {
    private Result result;
}
