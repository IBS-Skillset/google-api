package com.google.api.model.autoComplete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Predictions {
    private String description;
    @JsonProperty("matched_substrings")
    private List<MatchedSubstrings> matchedSubstrings;
    @JsonProperty("place_id")
    private String placeId;
    private List<Terms> terms;

}
