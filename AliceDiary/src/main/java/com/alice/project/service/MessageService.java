package com.alice.project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.domain.Message;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.MessageRepository;
import com.alice.project.web.MessageDto;
import com.alice.project.web.MsgListDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true) // 기본적으로 못바꾸게 해놓고
@RequiredArgsConstructor // final 붙은 필드만 가진 생성자 만들어줌
@Slf4j
public class MessageService {
   
   private final MemberService ms;
   private final MessageRepository messageRepository;
   private final MemberRepository memberRepository;
   
   public List<Message> findUserMsg(Long userNum) {
      HashMap<Long, Message> map = new HashMap<>();
      List<Message> msgList = new ArrayList<>();
      Long key;
      msgList = messageRepository.findByUserNum(userNum);
      if (msgList == null) {
         return null;
      }
      for (Message m : msgList) {
         if (userNum == m.getUser1Num()) {
            key = m.getUser2Num();
         } else {
            key = m.getUser1Num();
         }
         if (map.get(key) == null) {
            map.put(key, m);
         } else {
            if (m.getSendDate().isAfter(map.get(key).getSendDate())) {
               map.put(key, m);
            }
         }
      }
      List<Message> resultList = new ArrayList<>();
      
      for (Long k : map.keySet()) {
         resultList.add(map.get(k));
      }
      Collections.sort(resultList);
      return resultList;
   }
   
   
   public List<Message> findUserConv(Long userNum, Long youNum) {
      List<Message> msgList = new ArrayList<>();
      msgList = messageRepository.findByUserConv(userNum, youNum);
      
      if (msgList == null) {
         return null;
      } else {
         Collections.sort(msgList);
      }

      return msgList;
   }
   
   @Transactional
   public Integer changeMsgStatus(String fromId, String toId) {
      Long fromNum = findNumById(fromId);
      Long toNum = findNumById(toId);
      Boolean flag = false;
      if (fromNum < toNum) {
         flag = true;
      }

      messageRepository.changeMsgStatus(fromNum, toNum, flag);
      return 1;
   }
   
   // 보낸사람이 000인 받은사람 번호 리스트 받아오기
   public List<Message> findMsgsBySenderNum(Long num) {
      return messageRepository.findByMessageFromNum(num);
   }
   
   public String findIdByNum(Long num) {
      Member member = memberRepository.findByNum(num);
      return member.getId();
   }
   
   /* 아이디로 회원번호 찾기 */
   public Long findNumById(String id) {
      if (memberRepository.findById(id) == null) {
         return 0L;
      }
      Member member = memberRepository.findById(id);
      return member.getNum();
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
//      Collections.sort(msgs);
      Collections.reverse(msgs);
      
      for (Message msg : msgs) {
         log.info("*****************내용 각각 : " + msg.getContent());
      }
      
      return msgs;
      
   }
   
   // 사용자측에서 삭제하지 않은 메시지만 가져오기
   public List<Message> findLiveMsgs(Long mfn, Long mtn) {
      List<Message> msgF = messageRepository.findLiveMsgs(mfn, mtn);
      //List<Message> msgT = messageRepository.findLiveMsgs(mtn, mfn);
      List<Message> msgs = new ArrayList<>();
      msgs.addAll(msgF);
      //msgs.addAll(msgT);
      Collections.sort(msgs);
//      Collections.reverse(msgs);
      
      for (Message msg : msgs) {
         log.info("*****************내용 각각 : " + msg.getContent());
      }
      
      return msgs;
      
   }
   
   /* f번이 t번과의 쪽지함 삭제(관계상태0으로 업데이트) */
   @Transactional
   public Integer cutMsgRelations(Long messageFromNum, Long messageToNum) {
      Integer fresult = messageRepository.updateMsgRelationFrom(messageFromNum, messageToNum);
      Integer tresult = messageRepository.updateMsgRelationTo(messageFromNum, messageToNum);
      
      return fresult+tresult; // 2이면 성공
   }
   
   /* 쪽지 전송 */
   @Transactional
   public Message sendMsg(MessageDto mdto) {
      Long msgStatus = 3L; // 기본 쪽지 상태 (양쪽 모두 안 지운 상태)
      Message message = new Message(
            mdto.getUser1Num(),
            mdto.getUser2Num(),
            mdto.getSendDate(),
            mdto.getContent(),
            msgStatus,   
            mdto.getDirection()
            );
      
      log.info("!!!!!!!!!!!!요기!!!!!! : " + message.toString());
      Message result = messageRepository.save(message);
      return result;
   }
   
   /* 쪽지 검색 */
//   public Message searchMsgById(String id) { // 아이디 like로 찾기
//      Long num = findNumById(id);
//      
//      List<Message> msgList = messageRepository.searchByUserNum(num);
//      Long msgStatus = 3L; // 기본 쪽지 상태 (양쪽 모두 안 지운 상태)
//      Message message = new Message(
//            mdto.getUser1Num(),
//            mdto.getUser2Num(),
//            mdto.getSendDate(),
//            mdto.getContent(),
//            msgStatus   
//            );
//      
//      log.info("!!!!!!!!!!!!요기!!!!!! : " + message.toString());
//      Message result = messageRepository.save(message);
//      return result;
//   }   
   
   public List<MsgListDto> searchMsgByContent(String content, Long num) {
      List<Message> msgList = messageRepository.searchByContent(content, num);
      List<MsgListDto> mldtos = new ArrayList<>();

      Long receiverNum = 0L;
      if (msgList == null) { return null; }
      for (Message m : msgList) {
         if (num == m.getUser1Num()) {
            if (m.getMsgStatus() < 2) {
               continue;
            }
            receiverNum = m.getUser2Num();
         } else {
            if (m.getMsgStatus() % 2 == 0) {
               continue;
            }
            receiverNum = m.getUser1Num();
         }
         MsgListDto mldto = new MsgListDto();
         mldto.setUser1Num(m.getUser1Num());
         mldto.setUser2Num(m.getUser2Num());
         mldto.setSendDate(m.getSendDate());
         mldto.setRecentContent(m.getContent());
         mldto.setMessageToId(ms.findOne(receiverNum).getId());
         mldto.setMessageFromId(ms.findOne(num).getId());
         mldto.setDirection(m.getDirection());
         mldtos.add(mldto);
      }
      
      return mldtos;
   }   

   
}