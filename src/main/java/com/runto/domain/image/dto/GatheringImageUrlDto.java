package com.runto.domain.image.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GatheringImageUrlDto {

    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*\\.(webp)$", message = "유효하지 않은 이미지 URL입니다.")
    private String thumbnailUrl;

    @Valid
    @Size(max = 3, message = "본문 이미지 등록은 최대 3개까지만 허용됩니다.")
    private List<ContentImageUrlDto> contentImageUrls;

}
