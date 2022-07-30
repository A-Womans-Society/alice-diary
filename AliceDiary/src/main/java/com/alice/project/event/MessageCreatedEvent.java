package com.alice.project.event;

import com.alice.project.domain.Message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MessageCreatedEvent {

    private final Message message;

}

