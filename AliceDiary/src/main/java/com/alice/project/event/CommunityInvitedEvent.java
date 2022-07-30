package com.alice.project.event;

import com.alice.project.domain.Community;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommunityInvitedEvent {

    private final Community community;

}

