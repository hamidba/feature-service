package com.swisscom.feature.service;

import com.swisscom.feature.controller.external.request.FeatureRequest;
import com.swisscom.feature.controller.external.request.FeatureRequestItem;
import com.swisscom.feature.controller.external.response.FeatureResponse;
import com.swisscom.feature.controller.external.response.FeatureResponseItem;
import com.swisscom.feature.entity.Feature;
import com.swisscom.feature.repository.FeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ExternalFeatureService {

    private final FeatureRepository featureRepository;


    /**
     * Get customerFeatures
     *
     * @param featureRequest the request to get customer's feature
     * @return the features of a given customer
     */
    public FeatureResponse getCustomerFeatures(FeatureRequest featureRequest) {
        //Assumption : External API call will use technicalName for the request
        List<FeatureResponseItem> featureResponseItems = featureRepository.findByTechnicalNameIn(featureRequest.getFeatures().stream().map(FeatureRequestItem::getName).collect(Collectors.toList()))
                .stream()
                .map(f -> mapToFeatureResponse(featureRequest, f))
                .collect(Collectors.toList());

        return FeatureResponse.builder()
                .features(featureResponseItems).build();

    }

    private FeatureResponseItem mapToFeatureResponse(FeatureRequest featureRequest, Feature feature) {
        //Assumption : A feature is active when in list of customer && !inverted && !expired
        return FeatureResponseItem.builder()
                .name(feature.getTechnicalName())
                .inverted(feature.getInverted())
                .expired(feature.getExpiresOn().isBefore(LocalDate.now()))
                .active(feature.customerIsInList(featureRequest.getCustomerId()) && !feature.isInverted() && !feature.isExpired())
                .build();
    }

}
