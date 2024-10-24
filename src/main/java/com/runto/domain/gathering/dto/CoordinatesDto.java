package com.runto.domain.gathering.dto;

import com.runto.domain.gathering.domain.Coordinates;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CoordinatesDto {

    @NotNull(message = "x값은 필수입니다.")
    private Double x;

    @NotNull(message = "y값은 필수입니다.")
    private Double y;

    public Coordinates toCoordinates() {
        return new Coordinates(x, y);
    }
}
