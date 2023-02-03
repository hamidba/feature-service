package com.swisscom.feature.controller.external.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class FeatureRequest {

    @NotNull
    private Long customerId;
    @NotNull
    private List<FeatureRequestItem> features;

}
