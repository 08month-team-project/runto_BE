package com.runto.domain.image.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class ContentImageUrlDto {

    //boolean isThumbnail; // 변경될 여지가 있음
    private String imageUrl;
    private int order;
}
