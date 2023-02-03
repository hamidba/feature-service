package com.swisscom.feature.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.swisscom.feature.controller.external.request.FeatureRequest;
import com.swisscom.feature.controller.external.request.FeatureRequestItem;
import com.swisscom.feature.entity.Customer;
import com.swisscom.feature.entity.Feature;
import com.swisscom.feature.service.dto.FeatureDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

import static java.util.Collections.singletonList;

/**
 * Utility class for testing REST controllers.
 */
public final class TestUtil {

    public static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    public static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    public static final String DEFAULT_TECHNICAL_NAME = "AAAAAAAAAA";
    public static final String UPDATED_TECHNICAL_NAME = "BBBBBBBBBB";

    public static final LocalDate DEFAULT_EXPIRES_ON = LocalDate.ofEpochDay(0L);
    public static final LocalDate UPDATED_EXPIRES_ON = LocalDate.now(ZoneId.systemDefault());

    public static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    public static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    public static final Boolean DEFAULT_INVERTED = true;
    public static final Boolean UPDATED_INVERTED = false;

    public static final String DEFAULT_CUSTOMER_NAME = "Customer";


    private static final ObjectMapper mapper = createObjectMapper();

    private TestUtil() {}


    public static Feature createEntity() {
        Feature feature = Feature.builder()
                .displayName(DEFAULT_DISPLAY_NAME)
                .technicalName(DEFAULT_TECHNICAL_NAME)
                .expiresOn(DEFAULT_EXPIRES_ON)
                .description(DEFAULT_DESCRIPTION)
                .inverted(DEFAULT_INVERTED).build();
        return feature;
    }

    public static Feature createUpdatedEntity() {
        Feature feature = Feature.builder()
                .displayName(UPDATED_DISPLAY_NAME)
                .technicalName(UPDATED_TECHNICAL_NAME)
                .expiresOn(UPDATED_EXPIRES_ON)
                .description(UPDATED_DESCRIPTION)
                .inverted(UPDATED_INVERTED).build();
        return feature;
    }

    public static FeatureDTO createDtoFromEntity(Feature feature) {
        return FeatureDTO.builder()
                .id(feature.getId())
                .displayName(feature.getDisplayName())
                .technicalName(feature.getTechnicalName())
                .expiresOn(feature.getExpiresOn())
                .description(feature.getDescription())
                .inverted(feature.getInverted()).build();
    }

    public static FeatureRequest createFeatureRequest(Feature feature, Customer customer) {
        return FeatureRequest.builder()
                .customerId(customer.getId())
                .features(singletonList(createFutureRequestItem(feature.getTechnicalName())))
                .build();
    }

    public static FeatureRequestItem createFutureRequestItem(String name) {
        return FeatureRequestItem.builder()
                .name(name)
                .build();
    }

    public static Customer createCustomer() {
        return Customer.builder()
                .name(DEFAULT_CUSTOMER_NAME).build();
    }


    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    /**
     * Convert an object to JSON byte array.
     *
     * @param object the object to convert.
     * @return the JSON byte array.
     * @throws IOException
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }
}
