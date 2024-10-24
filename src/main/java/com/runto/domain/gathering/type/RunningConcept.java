package com.runto.domain.gathering.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RunningConcept {

    RUNLINI("런린이"),
    GOINMUL("고인물"),
    MARATHON("마라톤"),
    MORNING_RUNNING("모닝런닝"),
    EVENING_RUNNING("퇴근런닝"),
    HEALTH("건강");

    private final String description;
}
