package com.swisscom.feature.service;

import com.swisscom.feature.entity.Feature;
import com.swisscom.feature.repository.CustomerRepository;
import com.swisscom.feature.repository.FeatureRepository;
import com.swisscom.feature.service.dto.FeatureDTO;
import com.swisscom.feature.service.mapper.FeatureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeatureService {

    private final Logger log = LoggerFactory.getLogger(FeatureService.class);

    private final FeatureRepository featureRepository;
    private final CustomerRepository customerRepository;
    private final FeatureMapper featureMapper;

    public FeatureService(FeatureRepository featureRepository, CustomerRepository customerRepository, FeatureMapper featureMapper) {
        this.featureRepository = featureRepository;
        this.customerRepository = customerRepository;
        this.featureMapper = featureMapper;
    }

    /**
     * Save a feature.
     *
     * @param featureDTO the entity to save.
     * @return the persisted entity.
     */
    public FeatureDTO save(FeatureDTO featureDTO) {
        log.debug("Request to save Feature : {}", featureDTO);
        Feature feature = featureMapper.toEntity(featureDTO);
        feature = featureRepository.save(feature);
        return featureMapper.toDto(feature);
    }


    /**
     * Get all the features.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FeatureDTO> findAll() {
        log.debug("Request to get all Features");
        return featureRepository.findAll().stream().map(featureMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one feature by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeatureDTO> findOne(Long id) {
        log.debug("Request to get Feature : {}", id);
        return featureRepository.findById(id).map(featureMapper::toDto);
    }

    /**
     * Delete the feature by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Feature : {}", id);
        featureRepository.deleteById(id);
    }

}
