package com.google.api.model.placeId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
public class Location {
    private String lat;
    private String lng;
}
