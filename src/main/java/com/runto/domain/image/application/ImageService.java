package com.runto.domain.image.application;

import com.runto.domain.image.dto.ImageUploadRequest;
import com.runto.domain.image.dto.ImageUrlDto;
import com.runto.domain.image.dto.GatheringImageUrlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final S3GatheringImageService s3GatheringImageService;

    public GatheringImageUrlDto registerGatheringImages(MultipartFile thumbnail,
                                                        List<MultipartFile> contentImages,
                                                        int[] contentImageOrder) {

        ImageUrlDto thumbnailUrl = s3GatheringImageService
                .uploadThumbnail(ImageUploadRequest.from(thumbnail));

        List<ImageUrlDto> contentImageUrls = s3GatheringImageService
                .uploadContentImages(ImageUploadRequest.createRequestList(contentImages, contentImageOrder));

        return GatheringImageUrlDto.of(thumbnailUrl, contentImageUrls);
    }
}
