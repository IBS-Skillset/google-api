package com.google.api.model.autoComplete;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
public class AutoCompleteResponse implements Serializable {
    private List<Place> place;
}
