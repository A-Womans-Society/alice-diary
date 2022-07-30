package com.alice.project.event;

import com.alice.project.domain.Reply;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReplyCreatedEvent {

    private final Reply reply;

}

