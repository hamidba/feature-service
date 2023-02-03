package com.swisscom.feature.controller.external;

import com.swisscom.feature.controller.external.request.FeatureRequest;
import com.swisscom.feature.controller.external.response.FeatureResponse;
import com.swisscom.feature.service.ExternalFeatureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class ExternalFeatureController {

    private final ExternalFeatureService externalFeatureService;

    /**
     * {@code POST  /features} : Create a new feature.
     *
     * @param featureRequest the featureRequest
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new featureResponse
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/features")
    public ResponseEntity<FeatureResponse> getCustomerFeatures(@Valid @RequestBody FeatureRequest featureRequest) throws URISyntaxException {
        log.debug("REST request to get features for customer : {}", featureRequest.getCustomerId());
        FeatureResponse featureResponse = externalFeatureService.getCustomerFeatures(featureRequest);

        return ResponseEntity.ok().body(featureResponse);
    }


}
