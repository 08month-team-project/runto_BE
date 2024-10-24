package com.runto.domain.image.api;

import com.runto.domain.image.application.ImageService;
import com.runto.domain.image.dto.GatheringImageUrlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/gathering")
    public ResponseEntity<GatheringImageUrlDto> registerGatheringImages(
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestPart(value = "content_images", required = false) List<MultipartFile> contentImages,
            @RequestPart(value = "content_image_order", required = false) int[] contentImageOrder) { // 안에 하나하나 null 체크 하는 것보다 0으로 받기로 함

        return ResponseEntity.ok(imageService.
                registerGatheringImages(thumbnail, contentImages, contentImageOrder));

    }
}
