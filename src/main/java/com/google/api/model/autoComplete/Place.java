package com.google.api.model.autoComplete;

import lombok.*;

import java.io.Serializable;

@Data
public class Place implements Serializable {
    private String description;
    private String placeId;
}
