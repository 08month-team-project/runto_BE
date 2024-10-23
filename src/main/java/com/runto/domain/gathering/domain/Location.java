package com.runto.domain.gathering.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {

    @Column(name = "address_name")
    private String addressName;

    @Embedded
    private Coordinates coordinates;
}
