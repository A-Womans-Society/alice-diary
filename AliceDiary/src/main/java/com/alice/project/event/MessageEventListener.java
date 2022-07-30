package com.alice.project.event;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import com.alice.project.config.AppProperties;
import com.alice.project.domain.Member;
import com.alice.project.domain.Message;
import com.alice.project.domain.Notification;
import com.alice.project.domain.NotificationType;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.MessageRepository;
import com.alice.project.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class MessageEventListener {

	private final MessageRepository messageRepository;
	private final MemberRepository memberRepository;
	private final TemplateEngine templateEngine;
	private final AppProperties appProperties;
	private final NotificationRepository notificationRepository;

	@EventListener
	public void handleMessageCreatedEvent(MessageCreatedEvent messageCreatedEvent) {
		Message message = messageRepository.findByNum(messageCreatedEvent.getMessage().getNum());
		Long dir = message.getDirection();
		String senderName = "";
		Long receiverNum = 0L;
		if (dir == 0) {
			Long senderNum = message.getUser1Num();
			senderName = messageRepository.findByUser1Num(senderNum);
			receiverNum = message.getUser2Num();
		} else if (dir == 1) {
			Long senderNum = message.getUser2Num();
			senderName = messageRepository.findByUser1Num(senderNum);
			receiverNum = message.getUser1Num();
		}
		
		Member member = memberRepository.findByNum(receiverNum); // 받는 멤버객체
		if (member.isMessageCreated()) {
			createNotification(message, member, senderName + "으로부터 쪽지가 도착했습니다!", NotificationType.MESSAGE);
		}
	}

	private void createNotification(Message message, Member member, String comment, NotificationType notificationType) {
		Notification notification = new Notification();
		Long dir = message.getDirection();
		String senderName = "";
		if (dir == 0) {
			Member m = memberRepository.findByNum(message.getUser1Num()); // 보내는 사람
			senderName = m.getName();
		} else if (dir == 1) {
			Member m = memberRepository.findByNum(message.getUser2Num()); // 받는 사람
			senderName = m.getName();
		}
		notification.setTitle("쪽지가 도착했습니다!");
		notification.setLink("/messagebox/" + member.getId());
		notification.setChecked(false);
		notification.setCreatedDateTime(LocalDateTime.now());
		notification.setComment(comment);
		notification.setMember(member);
		notification.setNotificationType(notificationType);
		notificationRepository.save(notification);
	}

}
