package com.google.api.model.autoComplete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoCompletionResponse {
    private List<Predictions> predictions;
    private String status;
}
