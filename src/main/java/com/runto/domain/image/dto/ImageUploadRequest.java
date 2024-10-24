package com.runto.domain.image.dto;

import com.runto.domain.image.exception.ImageException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.runto.global.exception.ErrorCode.IMAGE_ORDER_MISMATCH;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ImageUploadRequest {

    private MultipartFile multipartFile;
    private int order;


    public static List<ImageUploadRequest> createRequestList(
            List<MultipartFile> multipartFiles,
            int[] contentImageOrder) {

        if (multipartFiles == null || multipartFiles.size() < 1) {
            return null;
        }
        if (contentImageOrder == null || contentImageOrder.length != multipartFiles.size()) {
            throw new ImageException(IMAGE_ORDER_MISMATCH);
        }

        List<ImageUploadRequest> result = new ArrayList<>();

        IntStream.range(0, multipartFiles.size())
                .forEach(i -> result.add(new ImageUploadRequest(multipartFiles.get(i), contentImageOrder[i])));

        return result;
    }

    // 순서 데이터 사용하지 않는 경우 사용 ex)썸네일
    public static ImageUploadRequest from(MultipartFile multipartFile) {

        return ImageUploadRequest.builder()
                .multipartFile(multipartFile)
                .build();
    }
}
