package com.google.api.model.placeId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceResponse implements Serializable {

    private String latitude;
    private String longitude;

}
