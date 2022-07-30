package com.alice.project.domain;

//새로운 쪽지왔을 때 (message) -> NewMessageEvent
//앨리스 알림 등록 시 (calendar)
//누군가 나를 친구추가했을 때 (friend)
//커뮤니티 초대됐을 때 (community)
//내 글에 댓글 달렸을 때 (post-reply)

public enum NotificationType {
	MESSAGE, ALICE, FRIEND, COMMUNITY, REPLY;
}
