package com.google.api.model.autoComplete;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Place implements Serializable {
    private String description;
    private String placeId;
}
