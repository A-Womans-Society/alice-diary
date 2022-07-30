package com.alice.project.event;

import com.alice.project.domain.Calendar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AliceCreatedEvent {

    private final Calendar calendar;

}

