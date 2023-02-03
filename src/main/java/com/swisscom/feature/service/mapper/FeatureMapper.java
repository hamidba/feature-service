package com.swisscom.feature.service.mapper;

import com.swisscom.feature.entity.Feature;
import com.swisscom.feature.service.dto.FeatureDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Feature} and its DTO {@link FeatureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeatureMapper extends EntityMapper<FeatureDTO, Feature> {}
