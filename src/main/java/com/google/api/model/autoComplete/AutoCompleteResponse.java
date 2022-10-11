package com.google.api.model.autoComplete;

import java.util.List;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AutoCompleteResponse {
    private List<Place> place;
}
