package com.alice.project.event;

import com.alice.project.domain.Friend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendAddEvent {

    private final Friend friend;

}

