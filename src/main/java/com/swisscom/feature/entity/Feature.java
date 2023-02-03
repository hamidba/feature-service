package com.swisscom.feature.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "feature")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "technical_name", nullable = false)
    private String technicalName;

    @Column(name = "expires_on", nullable = false)
    private LocalDate expiresOn;

    @Column(name = "description")
    private String description;

    @Column(name = "inverted", nullable = false)
    private Boolean inverted = false;

    @ManyToMany
    @JoinTable(
            name = "customer_feature",
            joinColumns = { @JoinColumn(name = "feature_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "customer_id", referencedColumnName = "id") }
    )
    @BatchSize(size = 20)
    private Set<Customer> customers = new HashSet<>();



    public Boolean customerIsInList(Long customerId){
        return customers.stream().map(Customer::getId).anyMatch(id -> id.equals(customerId));
    }

    public Boolean isExpired(){
        return getExpiresOn().isBefore(LocalDate.now());
    }

    public Boolean isInverted(){
        return getInverted();
    }




// customerIds: string[]

}
