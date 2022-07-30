package com.alice.project.event;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import com.alice.project.config.AppProperties;
import com.alice.project.domain.Calendar;
import com.alice.project.domain.Member;
import com.alice.project.domain.Notification;
import com.alice.project.domain.NotificationType;
import com.alice.project.repository.CalendarRepository;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class AliceEventListener {

	private final CalendarRepository calendarRepository;
	private final MemberRepository memberRepository;
	private final TemplateEngine templateEngine;
	private final AppProperties appProperties;
	private final NotificationRepository notificationRepository;

	@EventListener
	public void handleAliceCreatedEvent(AliceCreatedEvent aliceCreatedEvent) {
		Calendar calendar = calendarRepository.findByNum(aliceCreatedEvent.getCalendar().getNum());
		Member member = calendar.getMember();
		if (member.isAliceCreated()) {
			createNotification(calendar, member, "새로운 앨리스 알림입니다!", NotificationType.ALICE);
		}
	}

	private void createNotification(Calendar calendar, Member member, String comment, NotificationType notificationType) {
		Notification notification = new Notification();
		notification.setTitle("새로운 앨리스 알림입니다!");
		notification.setLink("/alice/");
		notification.setChecked(false);
		notification.setCreatedDateTime(LocalDateTime.now());
		notification.setComment(comment);
		notification.setMember(member);
		notification.setNotificationType(notificationType);
		notificationRepository.save(notification);
	}

}
