package com.runto.domain.gathering.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class Coordinates {
    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;
}
