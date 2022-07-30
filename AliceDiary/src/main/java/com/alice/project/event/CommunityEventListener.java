package com.alice.project.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import com.alice.project.config.AppProperties;
import com.alice.project.domain.Community;
import com.alice.project.domain.Member;
import com.alice.project.domain.Message;
import com.alice.project.domain.Notification;
import com.alice.project.domain.NotificationType;
import com.alice.project.repository.CommunityRepository;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class CommunityEventListener {

	private final CommunityRepository communityRepository;
	private final MemberRepository memberRepository;
	private final TemplateEngine templateEngine;
	private final AppProperties appProperties;
	private final NotificationRepository notificationRepository;

	@EventListener
	public void handleCommunityInvitedEvent(CommunityInvitedEvent communityInvitedEvent) {
		Community community = communityRepository.findByNum(communityInvitedEvent.getCommunity().getNum());
		String memberList = community.getMemberList();
		List<Member> members = new ArrayList<>();
		for (String f : memberList.split(",")) {
			members.add(memberRepository.findById(f));
		}
		members.forEach(member -> {
			if (member.isCommunityInvited()) {
				createNotification(community, member, "새로운 커뮤니티에 초대되었습니다.", NotificationType.COMMUNITY);
				
			}
		});
	}

	private void createNotification(Community community, Member member, String comment, NotificationType notificationType) {
		Notification notification = new Notification();
		notification.setTitle("새로운 커뮤니티 초대 알림");
		notification.setLink("/AliceDiary/community/checkExist");
		notification.setChecked(false);
		notification.setCreatedDateTime(LocalDateTime.now());
		notification.setComment(comment);
		notification.setMember(member);
		notification.setNotificationType(notificationType);
		notificationRepository.save(notification);
	}

}
