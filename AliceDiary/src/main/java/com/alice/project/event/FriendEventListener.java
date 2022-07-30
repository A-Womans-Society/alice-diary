package com.alice.project.event;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import com.alice.project.config.AppProperties;
import com.alice.project.domain.Friend;
import com.alice.project.domain.Member;
import com.alice.project.domain.Notification;
import com.alice.project.domain.NotificationType;
import com.alice.project.repository.FriendRepository;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class FriendEventListener {

	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;
	private final NotificationRepository notificationRepository;

	@EventListener
	public void handleFriendAddEvent(FriendAddEvent friendAddEvent) {
		Friend friend = friendRepository.searchByFriendNum(friendAddEvent.getFriend().getNum());
		Member member = memberRepository.findByNum(friend.getAddeeNum());
		if (member.isFriendAdded()) {
			createNotification(friend, member, "누군가 나를 친구로 추가했습니다.", NotificationType.FRIEND);
		}
	}

	private void createNotification(Friend friend, Member member, String comment, NotificationType notificationType) {
		Notification notification = new Notification();
		notification.setTitle("새로운 친구 추가 알림");
		notification.setLink("/AliceDiary/friend/");
		notification.setChecked(false);
		notification.setCreatedDateTime(LocalDateTime.now());
		notification.setComment(comment);
		notification.setMember(member);
		notification.setNotificationType(notificationType);
		notificationRepository.save(notification);
	}

}
