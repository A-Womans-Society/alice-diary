package com.alice.project.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Community;
import com.alice.project.domain.Member;
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
public class CommunityEventListener implements ApplicationListener<CommunityInvitedEvent> {

	private final CommunityRepository communityRepository;
	private final MemberRepository memberRepository;
	private final NotificationRepository notificationRepository;

	private void createNotification(Community community, Member member, String wording, NotificationType notificationType) {
		Notification notification = new Notification();
		notification.setTitle("커뮤니티 탭에서 확인해주세요.");
		notification.setLink("/community/" + community.getNum() + "/list");
		notification.setChecked(false);
		notification.setCreatedDateTime(LocalDateTime.now());
		notification.setWording(wording);
		notification.setMember(member);
		notification.setNotificationType(notificationType);
		notificationRepository.save(notification);
		log.info("커뮤 노티 세이브~");
	}

	@Override
	public void onApplicationEvent(CommunityInvitedEvent event) {
		Community community = communityRepository.findByNum(event.getCommunity().getNum());
		log.info("커뮤니티 이름 : " + community.getName());
		String memberList = community.getMemberList();
		if (memberList.length() != 0) {
			List<Member> members = new ArrayList<>();
			for (String f : memberList.split(",")) {
				members.add(memberRepository.findById(f));
			}
			members.forEach(member -> {
				if (member.isCommunityInvited()) {
					createNotification(community, member, member.getName() + "님이 새로운 커뮤니티에 초대되었습니다!", NotificationType.COMMUNITY);
				}
			});		
		}
		
	}

}
