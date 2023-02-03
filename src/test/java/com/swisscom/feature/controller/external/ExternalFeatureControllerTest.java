package com.swisscom.feature.controller.external;

import com.swisscom.feature.IntegrationTest;
import com.swisscom.feature.controller.external.request.FeatureRequest;
import com.swisscom.feature.entity.Customer;
import com.swisscom.feature.entity.Feature;
import com.swisscom.feature.repository.CustomerRepository;
import com.swisscom.feature.repository.FeatureRepository;
import com.swisscom.feature.utils.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.swisscom.feature.utils.TestUtil.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExternalFeatureControllerTest} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
public class ExternalFeatureControllerTest {

    private static final String ENTITY_API_URL = "/api/v1/features";

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc restFeatureMockMvc;

    private Feature feature;
    private FeatureRequest featureRequest;
    private Customer customer;

    @BeforeEach
    public void initTest() {
        customer = customerRepository.save(createCustomer());
        Feature newFeature = createEntity();
        newFeature.setCustomers(Set.of(customer));
        feature = featureRepository.save(newFeature);
        featureRequest = createFeatureRequest(feature, customer);
    }

    @Test
    @Transactional
    void getCustomerFeatureInTheList() throws Exception {

        restFeatureMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(featureRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.features[0].name").value(feature.getTechnicalName()))
                .andExpect(jsonPath("$.features[0].expired").value(feature.isExpired()))
                .andExpect(jsonPath("$.features[0].inverted").value(feature.isInverted()))
                .andExpect(jsonPath("$.features[0].active").value(feature.customerIsInList(customer.getId()) && !feature.isInverted() && !feature.isExpired()));

    }


}
