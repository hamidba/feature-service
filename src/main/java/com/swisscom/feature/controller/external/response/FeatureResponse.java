package com.swisscom.feature.controller.external.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FeatureResponse {

    private List<FeatureResponseItem> features;
}
