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
import com.alice.project.domain.Reply;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.NotificationRepository;
import com.alice.project.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class ReplyEventListener {

	private final ReplyRepository replyRepository;
	private final MemberRepository memberRepository;
	private final TemplateEngine templateEngine;
	private final AppProperties appProperties;
	private final NotificationRepository notificationRepository;

	@EventListener
	public void handleReplyCreatedEvent(ReplyCreatedEvent replyCreatedEvent) {
		Reply reply = replyRepository.findByNum(replyCreatedEvent.getReply().getNum());
		Member member = reply.getPost().getMember();
		if (member.isReplyCreated()) {
			createNotification(reply, member, "내 글에 새로운 댓글이 달렸습니다.", NotificationType.REPLY);
		}
	}

	private void createNotification(Reply reply, Member member, String comment, NotificationType notificationType) {
		Notification notification = new Notification();
		notification.setTitle("새로운 댓글 알림");
		notification.setLink("/AliceDiary/open/list"); // 일단 공개게시판으로 해놓자...
		notification.setChecked(false);
		notification.setCreatedDateTime(LocalDateTime.now());
		notification.setComment(comment);
		notification.setMember(member);
		notification.setNotificationType(notificationType);
		notificationRepository.save(notification);
	}

}
