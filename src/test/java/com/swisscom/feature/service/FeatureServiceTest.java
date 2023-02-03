package com.swisscom.feature.service;

import com.swisscom.feature.entity.Feature;
import com.swisscom.feature.repository.FeatureRepository;
import com.swisscom.feature.service.dto.FeatureDTO;
import com.swisscom.feature.service.mapper.FeatureMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.swisscom.feature.utils.TestUtil.createDtoFromEntity;
import static com.swisscom.feature.utils.TestUtil.createEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FeatureServiceTest {

    @InjectMocks
    private FeatureService featureService;
    @Mock
    private FeatureRepository featureRepository;
    @Mock
    private FeatureMapper featureMapper;

    private Feature feature;
    private FeatureDTO featureDTO;


    @BeforeEach
    public void initTest() {
        feature = createEntity();
        featureDTO = createDtoFromEntity(feature);
    }


    @Test
    void save() {
        when(featureMapper.toEntity(featureDTO)).thenReturn(feature);
        when(featureMapper.toDto(feature)).thenReturn(featureDTO);
        when(featureRepository.save(any(Feature.class))).thenReturn(feature);

        final FeatureDTO savedFeatureDTO = featureService.save(featureDTO);

        assertThat(featureDTO).usingRecursiveComparison().isEqualTo(savedFeatureDTO);
        verify(featureRepository, times(1)).save(any(Feature.class));
        verifyNoMoreInteractions(featureRepository);

    }

    @Test
    void findAll() {
        when(featureRepository.findAll()).thenReturn(Collections.singletonList(feature));
        when(featureMapper.toDto(feature)).thenReturn(featureDTO);

        final List<FeatureDTO> featureDTOS = featureService.findAll();

        assertThat(featureDTOS).hasSize(1);
        verify(featureRepository, times(1)).findAll();
        verify(featureMapper, times(1)).toDto(any(Feature.class));
        verifyNoMoreInteractions(featureRepository);

    }

    @Test
    void findOne() {
        feature.setId(1l);
        FeatureDTO featureDTO = createDtoFromEntity(feature);
        when(featureRepository.findById(feature.getId())).thenReturn(Optional.of(feature));
        when(featureMapper.toDto(feature)).thenReturn(featureDTO);

        final Optional<FeatureDTO> optionalFeatureDTO = featureService.findOne(1l);

        assertThat(optionalFeatureDTO).isPresent();
        assertThat(optionalFeatureDTO.get()).isEqualTo(featureDTO);
        verify(featureRepository, times(1)).findById(anyLong());
        verify(featureMapper, times(1)).toDto(any(Feature.class));
        verifyNoMoreInteractions(featureRepository);

    }

    @Test
    void delete() {
        featureService.delete(1l);
        verify(featureRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(featureRepository);

    }
}
