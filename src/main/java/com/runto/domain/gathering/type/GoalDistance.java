package com.runto.domain.gathering.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum GoalDistance {  // 실제 뛴 거리 X

    FREE(0.0, "free"),
    THREE_KM(3.0, "3km"),
    FIVE_KM(5.0, "5km"),
    FIFTEEN_KM(15.0, "15km"),
    HALF_MARATHON(21.0975, "하프 마라톤 (21.0975km)"),
    FULL_MARATHON(42.195, "풀 마라톤 (42.195km)");

    private final double distance;
    private final String description;

}
