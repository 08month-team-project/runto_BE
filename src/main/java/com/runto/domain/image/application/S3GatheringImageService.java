package com.runto.domain.image.application;

import com.runto.domain.image.dto.ImageUploadRequest;
import com.runto.domain.image.dto.ImageUrlDto;
import com.runto.domain.image.exception.ImageException;
import io.awspring.cloud.s3.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.runto.global.exception.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3GatheringImageService {

    private static final List<String> SUPPORT_IMAGE_EXTENSION = List.of("jpg", "jpeg", "png", "bmp", "webp");

    private static final String TEMPORARY_STORE_PREFIX = "temp/";

    private final S3Client s3Client;

    private final ImageCompressService imageCompressService;


    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.cloud.aws.region.static}")
    private String region;


    public List<ImageUrlDto> uploadContentImages(List<ImageUploadRequest> contentImages) {

        if (contentImages == null || contentImages.size() < 1) {
            return null;
        }
        List<ImageUrlDto> imageUrls = new ArrayList<>();

        for (ImageUploadRequest contentImageFile : contentImages) {
            imageUrls.add(processUploadImage(contentImageFile));
        }

        return imageUrls;
    }

    public ImageUrlDto uploadThumbnail(ImageUploadRequest imageUploadRequest) {

        if (imageUploadRequest == null || imageUploadRequest.getMultipartFile() == null) {
            return null;
        }
        return processUploadImage(imageUploadRequest);
    }


    private ImageUrlDto processUploadImage(ImageUploadRequest imageUploadRequest) {

        File convertedFile = null;
        File pressedFile = null;

        try {
            MultipartFile requestFile = imageUploadRequest.getMultipartFile();

            // 지원하는 이미지 확장자 파일인지 검증
            validateImageExtension(requestFile);

            // 고유의 이미지 이름 생성
            String imageName = createUniqueFileName(requestFile);

            // 파일 객체로 변환 & 서버에 임시저장
            convertedFile = convertToFile(imageName, requestFile);

            // 이미지 최적화 & 서버에 임시저장
            pressedFile = compressToWebp(requestFile.getName(), imageName, convertedFile);


            // s3에 업로드하기 위한 객체 생성
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(TEMPORARY_STORE_PREFIX + pressedFile.getName())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            // s3에 최적화한 이미지 업로드
            s3Client.putObject(putObjectRequest, RequestBody.fromFile(pressedFile));

            // 실제로 업로드가 잘 됐는지 검증
            validateUpload(pressedFile);

            // S3 URL과 , 요청에서 받아온 이미지 순서를 다시 넣어서 반환
            return new ImageUrlDto(getImageUrl(pressedFile), imageUploadRequest.getOrder());

        } catch (IOException | S3Exception e) {
            throw new ImageException(e, IMAGE_SERVER_ERROR);

        } finally {
            deleteFile(convertedFile);
            deleteFile(pressedFile);
        }

    }

    private String getImageUrl(File pressedFile) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region, TEMPORARY_STORE_PREFIX + pressedFile.getName());
    }

    private void validateUpload(File pressedFile) {

        // 업로드된 객체 확인 (headObject 사용)
        HeadObjectRequest headRequest = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(TEMPORARY_STORE_PREFIX + pressedFile.getName())
                .build();

        HeadObjectResponse response = s3Client.headObject(headRequest);

        if (response == null) {
            throw new ImageException(S3_OBJECT_NOT_FOUND);
        }
    }

    private String createUniqueFileName(MultipartFile multipartFile) {
        return multipartFile.getName()+ "-" + UUID.randomUUID();
    }

    /**
     * System.getProperty("user.dir") -> 현재 프로젝트 최상위 디렉토리
     * 프로젝트 최상위 폴더 하위에 images 라는 폴더를 만들어놓고 저장중인 상황
     */
    private File convertToFile(String uniqueName, MultipartFile multipartFile) throws IOException {

        File file = new File(System.getProperty("user.dir") +
                "/images/" + uniqueName + multipartFile.getName());

        multipartFile.transferTo(file); // 실제 파일 생성
        return file;
    }

    /**
     * 썸네일 이미지인 경우 일반압축
     * 본문 이미지의 경우 무손실 압축
     */
    private File compressToWebp(String partName, String fileName, File originalFile) {
        if (!"thumbnail".equals(partName)) {
            return imageCompressService.convertToWebpWithLossless(fileName, originalFile);
        }
        return imageCompressService.convertToWebp(fileName, originalFile);
    }

    private void validateImageExtension(MultipartFile multipartFile) {

        if (multipartFile == null || !StringUtils.hasText(multipartFile.getOriginalFilename())) {
            throw new ImageException(INVALID_FILE);
        }

        String extension = extractExtension(multipartFile.getOriginalFilename());
        if (!SUPPORT_IMAGE_EXTENSION.contains(extension)) {
            throw new ImageException(UNSUPPORTED_IMAGE_EXTENSION);
        }

    }

    private String extractExtension(String originalFilename) {

        return originalFilename
                .substring(originalFilename.indexOf(".") + 1);
    }

    private void deleteFile(File file) {
        if (file != null && file.delete()) {
            log.info("File 삭제 ={}", file.getName());
        }
    }
}
