package com.alice.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.domain.Message;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.MessageRepository;
import com.alice.project.web.MessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true) // 기본적으로 못바꾸게 해놓고
@RequiredArgsConstructor // final 붙은 필드만 가진 생성자 만들어줌
@Slf4j
public class MessageService {
	
	private final MessageRepository messageRepository;
	private final MemberRepository memberRepository;
	private final EntityManager em;
	
	// 보낸사람이 000인 받은사람 번호 리스트 받아오기
	public List<Message> findMsgsBySenderNum(Long num) {
		return messageRepository.findByMessageFromNum(num);
	}
	
	
	// 보낸사람이 000인 받은사람 번호 리스트 받아오기
	public List<Long> findReceiverNumsBySenderNum(Long num) {
		List<Long> receiverNumsList = new ArrayList<>();
		List<Message> messageList = messageRepository.findByMessageFromNum(num);
		log.info("MS진입");
		if (messageList.isEmpty()) { log.info("messageList가 널입니다!!"); return null; }
		for (Message msg : messageList) {
			if (!receiverNumsList.contains(msg.getMessageToNum())) {
				receiverNumsList.add(msg.getMessageToNum());		
				log.info("MS의 msg.getMessageToNum() : " + msg.getMessageToNum());
			}
		}
		for (Long n : receiverNumsList) {
			log.info("MS의 받은 사람 번호 리스트 : " + n);
		}
		return receiverNumsList;
	}	

	// 보낸사람이 000인 받은사람 번호 리스트 받아오기
	public List<Long> findLiveReceiverNumsBySenderNum(Long num) {
		List<Long> receiverNumsList = new ArrayList<>();
		List<Message> messageList = messageRepository.findByLiveMessageFromNum(num);
		log.info("MS진입");
		for (Message msg : messageList) {
			log.info("MS의 msg.getMessageToNum() : " + msg.getMessageToNum());

		}
		List<Long> nullList = new ArrayList<>();
		nullList.add(0L);
		if (messageList.isEmpty()) { log.info("messageList가 널입니다!!"); return nullList; }
		for (Message msg : messageList) {
			if (!receiverNumsList.contains(msg.getMessageToNum())) {
				receiverNumsList.add(msg.getMessageToNum());		
				log.info("MS의 msg.getMessageToNum() : " + msg.getMessageToNum());
			}
		}
		for (Long n : receiverNumsList) {
			log.info("MS의 받은 사람 번호 리스트 : " + n);
		}
		return receiverNumsList;
	}	

	public List<Long> findLiveReceiverNumsByReceiverNum(Long num) {
		List<Long> receiverNumsList = new ArrayList<>();
		List<Message> messageList = messageRepository.findMsgByLiveMessageToNum(num);
		log.info("MS진입");
		for (Message msg : messageList) {
			log.info("MS의 msg.getMessageToNum() : " + msg.getMessageToNum());

		}
		List<Long> nullList = new ArrayList<>();
		nullList.add(0L);
		if (messageList.isEmpty()) { log.info("messageList가 널입니다!!"); return nullList; }
		for (Message msg : messageList) {
			if (!receiverNumsList.contains(msg.getMessageToNum())) {
				receiverNumsList.add(msg.getMessageToNum());		
				log.info("MS의 msg.getMessageToNum() : " + msg.getMessageToNum());
			}
		}
		for (Long n : receiverNumsList) {
			log.info("MS의 받은 사람 번호 리스트 : " + n);
		}
		return receiverNumsList;
	}		
	
	public List<String> findReceiverIdsBySenderNum(Long num) {
		List<String> ids = new ArrayList<>();
		List<Long> nums = findReceiverNumsBySenderNum(num);
		if (nums.isEmpty()) { return null; }
		for (Long n : nums) {
			Member member = memberRepository.findByNum(n);
			ids.add(member.getId());
		}
		return ids;
	}
	
	public String findIdByNum(Long num) {
		Member member = memberRepository.findByNum(num);
		return member.getId();
	}
	
	public Long findNumById(String id) {
		Member member = memberRepository.findById(id);
		return member.getNum() != null ? member.getNum() : null;
	}	
	
	public Message findRecentMsgs(Long mfn, Long mtn) {
		if (messageRepository.findRecentMsgByNum(mfn, mtn) == null) {
			return null;
		}
		Message msg = messageRepository.findRecentMsgByNum(mfn, mtn);
		log.info("MS의 message : " + msg.toString());
		return msg;
	}
	
	public List<Message> findMsgs(Long mfn, Long mtn) {
		List<Message> msgF = messageRepository.findMsgs(mfn, mtn);
		List<Message> msgT = messageRepository.findMsgs(mtn, mfn);
		List<Message> msgs = new ArrayList<>();
		msgs.addAll(msgF);
		msgs.addAll(msgT);
//		Collections.sort(msgs);
		Collections.reverse(msgs);
		
		for (Message msg : msgs) {
			log.info("*****************내용 각각 : " + msg.getContent());
		}
		
		return msgs;
		
	}
	
	// 사용자측에서 삭제하지 않은 메시지만 가져오기
	public List<Message> findLiveMsgs(Long mfn, Long mtn) {
		List<Message> msgF = messageRepository.findLiveMsgs(mfn, mtn);
		List<Message> msgT = messageRepository.findLiveMsgs(mtn, mfn);
		List<Message> msgs = new ArrayList<>();
		msgs.addAll(msgF);
		msgs.addAll(msgT);
		Collections.sort(msgs);
//		Collections.reverse(msgs);
		
		for (Message msg : msgs) {
			log.info("*****************내용 각각 : " + msg.getContent());
		}
		
		return msgs;
		
	}
	
	
	/* 개별 쪽지함 삭제 */
	public void deleteOne(Long messageFromNum, Long messageToNum) {
		List<Message> messages = messageRepository.findByMessageFromNumAndMessageToNum(messageFromNum, messageToNum);
		for (Message msg : messages) {
			log.info("MS의 messages 하나 : " + msg);
		}
		messageRepository.deleteAll(messages);
	}
	
	/* f번이 t번과의 쪽지함 삭제(관계상태0으로 업데이트) */
	public Integer cutMsgRelations(Long messageFromNum, Long messageToNum) {
		Integer fresult = messageRepository.updateMsgRelationFrom(messageFromNum, messageToNum);
		Integer tresult = messageRepository.updateMsgRelationTo(messageFromNum, messageToNum);
		
		return fresult+tresult; // 2이면 성공
	}
	
	/* 쪽지 전송 */
	public Message sendMsg(MessageDto mdto) {
		Message message = new Message(mdto.getMessageFromNum(), mdto.getMessageToNum(),
				LocalDateTime.now(), mdto.getContent());
		log.info("!!!!!!!!!!!!요기!!!!!! : " + message.toString());
		Message result = messageRepository.save(message);
		em.flush();
		return result;
	}

	
}
