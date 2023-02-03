package com.swisscom.feature.controller.external.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureResponseItem {

    private String name;
    private Boolean active;
    private Boolean inverted;
    private Boolean expired;
}
