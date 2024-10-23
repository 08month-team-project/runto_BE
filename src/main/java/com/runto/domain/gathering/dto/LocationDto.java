package com.runto.domain.gathering.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.runto.domain.gathering.domain.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LocationDto {

    @NotBlank(message = "주소는 필수값입니다.")
    private String addressName;

    @NotNull(message = "위치 좌표는 필수값입니다.")
    private CoordinatesDto coordinates;

    public Location toLocation() {
        return Location.builder()
                .addressName(addressName)
                .coordinates(coordinates.toCoordinates())
                .build();
    }
}
