package com.swisscom.feature.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.swisscom.feature.entity.Feature} entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDTO implements Serializable {

    private Long id;

    @NotNull
    private String displayName;

    @NotNull
    private String technicalName;

    @NotNull
    private LocalDate expiresOn;

    private String description;

    @NotNull
    private Boolean inverted;


}
