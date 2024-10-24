package com.runto.domain.gathering.api;

import com.runto.domain.gathering.application.GatheringService;
import com.runto.domain.gathering.dto.CreateGatheringRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gatherings")
public class GatheringController {

    private final GatheringService gatheringService;

    // TODO: 회원관련 기능 dev에 머지되면 param 에 UserDetails 추가 & 교체
    @PostMapping
    public ResponseEntity<Void> createGathering(@Valid @RequestBody CreateGatheringRequest request) {

        gatheringService.createGatheringGeneral(request);
        return ResponseEntity.ok().build();
    }

}
