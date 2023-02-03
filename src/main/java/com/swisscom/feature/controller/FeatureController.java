package com.swisscom.feature.controller;

import com.swisscom.feature.repository.FeatureRepository;
import com.swisscom.feature.service.FeatureService;
import com.swisscom.feature.service.dto.FeatureDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.swisscom.feature.entity.Feature}.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class FeatureController {

    private final FeatureService featureService;
    private final FeatureRepository featureRepository;

    /**
     * {@code POST  /features} : Create a new feature.
     *
     * @param featureDTO the featureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new featureDTO, or with status {@code 400 (Bad Request)} if the feature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/features")
    public ResponseEntity<FeatureDTO> createFeature(@Valid @RequestBody FeatureDTO featureDTO) throws URISyntaxException {
        log.debug("REST request to save Feature : {}", featureDTO);
        if (Objects.nonNull(featureDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new feature cannot have an ID");
        }
        FeatureDTO result = featureService.save(featureDTO);
        return ResponseEntity
                .created(new URI("/api/features/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /features/:id} : Updates an existing feature.
     *
     * @param id the id of the featureDTO to save.
     * @param featureDTO the featureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featureDTO,
     * or with status {@code 400 (Bad Request)} if the featureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the featureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/features/{id}")
    public ResponseEntity<FeatureDTO> updateFeature(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody FeatureDTO featureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Feature : {}, {}", id, featureDTO);
        if (featureDTO.getId() == null || !Objects.equals(id, featureDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }
        if (!featureRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Feature not found");
        }

        FeatureDTO result = featureService.save(featureDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }



    /**
     * {@code GET  /features} : get all the features.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @GetMapping("/features")
    public List<FeatureDTO> getAllFeatures() {
        log.debug("REST request to get all Features");
        return featureService.findAll();
    }

    /**
     * {@code GET  /features/:id} : get the "id" feature.
     *
     * @param id the id of the featureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the featureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/features/{id}")
    public ResponseEntity<FeatureDTO> getFeature(@PathVariable Long id) {
        log.debug("REST request to get Feature : {}", id);
        Optional<FeatureDTO> featureDTO = featureService.findOne(id);
        if(!featureDTO.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature not found");
        }
        return ResponseEntity.ok().body(featureDTO.get());
    }

    /**
     * {@code DELETE  /features/:id} : delete the "id" feature.
     *
     * @param id the id of the featureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/features/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        log.debug("REST request to delete Feature : {}", id);
        featureService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}

