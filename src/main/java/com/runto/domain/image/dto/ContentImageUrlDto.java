package com.runto.domain.image.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.runto.domain.image.domain.GatheringImage;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class ContentImageUrlDto {

    //boolean isThumbnail; // 변경될 여지가 있음

    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*\\.(webp)$", message = "유효하지 않은 이미지 URL입니다.")
    private String imageUrl;

    private int order;

    public GatheringImage toEntity() {
        return GatheringImage.builder()
                .imageUrl(imageUrl)
                .imageOrder(order)
                .build();
    }
}
