package com.alice.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Friend;
import com.alice.project.domain.Member;
import com.alice.project.domain.Message;
import com.alice.project.handler.Alert;
import com.alice.project.service.FriendService;
import com.alice.project.service.MemberService;
import com.alice.project.service.MessageService;
import com.alice.project.web.FriendshipDto;
import com.alice.project.web.MessageDto;
import com.alice.project.web.MsgListDto;
import com.alice.project.web.MsgSearchDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;
	private final MemberService memberService;
	private final FriendService friendService;

	// 쪽지 목록 보기
	@GetMapping(value = "/messagebox/{id}")
	public String messageList(@PathVariable("id") String id, Model model, @ModelAttribute MessageDto mdto,
			MsgSearchDto msdto) {
		// log.info("현재 로그인회원번호 : " + session.getAttribute("num"));
		// String messageFromNum = (String) session.getAttribute("num");
		Long num = messageService.findNumById(id); // tester의 userNum = 1
		log.info("사용자 id : " + id);
		log.info("사용자 num : " + num);
		model.addAttribute("mdto", mdto);
		model.addAttribute("fromId", id);
		model.addAttribute("msdto", msdto);

		List<Message> msgList = new ArrayList<>();
		msgList = messageService.findUserMsg(num);
		if (msgList == null) {
			return "message/msgList";
		}

		List<MsgListDto> mldtos = new ArrayList<>();
		Long receiverNum = 0L;
		for (Message m : msgList) {
//			receiverNum = m.getUser1Num() == num ? m.getUser2Num() : m.getUser1Num(); 
			if (num == m.getUser1Num()) { // user1Num이 사용자라면 (2, 3만 보여야 함)
				if (m.getMsgStatus() < 2) { // 0, 1
					continue;
				}
				receiverNum = m.getUser2Num();
			} else { // user2Num이 사용자라면 (1, 3만 보여야 함)
				if (m.getMsgStatus() % 2 == 0) { // 0, 2
					continue;
				}
				receiverNum = m.getUser1Num();
			}
			MsgListDto mldto = new MsgListDto();
			mldto.setUser1Num(m.getUser1Num());
			mldto.setUser2Num(m.getUser2Num());
			mldto.setSendDate(m.getSendDate());
			mldto.setRecentContent(m.getContent());
			mldto.setMessageToId(memberService.findOne(receiverNum).getId());
			mldto.setMessageFromId(memberService.findOne(num).getId());
			mldto.setDirection(m.getDirection());
			mldtos.add(mldto);
		}

		// 친구목록 가져오기
		List<Friend> friendsList = friendService.friendship(num);
		List<FriendshipDto> friendsDtoList = new ArrayList<>();
		for (Friend f : friendsList) {
			FriendshipDto tmp = new FriendshipDto();
			Member m = memberService.findByNum(f.getAddeeNum());
			tmp.setNum(m.getNum());
			tmp.setName(m.getName());
			tmp.setId(m.getId());
			friendsDtoList.add(tmp);
		}
		model.addAttribute("friendsList", friendsDtoList);

		model.addAttribute("receiverNum", receiverNum);
		model.addAttribute("mldtos", mldtos);
		model.addAttribute("mdto", mdto);
		model.addAttribute("fromNum", num);

		return "message/msgList";
	}

	// 쪽지 목록에서 쪽지 보내기
	@PostMapping("/messagebox/{id}")
	public String sendMessage(@PathVariable("id") String id, Model model, HttpSession session,
			@ModelAttribute MessageDto mdto) {
		log.info("들오오니?????????????????????????????");
		Long senderNum = messageService.findNumById(id);
		Long receiverNum = messageService.findNumById(mdto.getMessageToId());

		if (senderNum > receiverNum) {
			mdto.setUser1Num(receiverNum);
			mdto.setUser2Num(senderNum);
			mdto.setDirection(1L);
		} else {
			mdto.setUser1Num(senderNum);
			mdto.setUser2Num(receiverNum);
			mdto.setDirection(0L);
		}

		mdto.setMessageFromId(id);
		mdto.setSendDate(LocalDateTime.now());
		log.info("mdto" + mdto.toString());

		Message result = messageService.sendMsg(mdto);
		log.info("result.toString() : " + result.toString());
		model.addAttribute("data", new Alert("메시지가 성공적으로 전송되었습니다!", "./" + id));
		return "message/alert";
	}

	// 쪽지함 삭제
	@PostMapping("/messagebox/{fromId}/delete")
	@ResponseBody
	public String deleteMessage(@PathVariable String fromId, String toId, Model model) {
		messageService.changeMsgStatus(fromId, toId);
		model.addAttribute("data", new Alert("쪽지가 성공적으로 삭제되었습니다!", ""));
		return "1";
	}

	// 개별 쪽지함 상세보기
	@GetMapping("/messagebox/{fromId}/{toId}")
	public String showMessages(@PathVariable("fromId") String fromId, @PathVariable("toId") String toId, Model model,
			HttpSession session, MessageDto mdto) {
		Long fromNum = messageService.findNumById(fromId);
		Long toNum = messageService.findNumById(toId);

		List<Message> msgList = messageService.findUserConv(fromNum, toNum);

		model.addAttribute("fromId", fromId);
		model.addAttribute("mdtos", msgList);

//		String toId = messageService.findIdByNum(toNum);
//		String userNum = session.getAttribute("userNum").toString();
		// 세션에서 사용자번호 받아서 넣기
//		model.addAttribute("userNum", 3); // ***테스트용으로 하드코딩한 것
		model.addAttribute("userNum", fromNum);
		model.addAttribute("toNum", toNum);
		model.addAttribute("toId", toId);
		model.addAttribute("mdto", mdto);

		return "message/msgDetail";
	}

	// 쪽지함 내에서 쪽지보내기
	@PostMapping("/messagebox/{fromId}/{toId}")
	public String sendMessages(@PathVariable("fromId") String fromId, @PathVariable("toId") String toId, Model model,
			@ModelAttribute MessageDto mdto) {
		Long senderNum = messageService.findNumById(fromId);
		Long receiverNum = messageService.findNumById(toId);

		if (senderNum > receiverNum) {
			mdto.setUser1Num(receiverNum);
			mdto.setUser2Num(senderNum);
			mdto.setDirection(1L);
		} else {
			mdto.setUser1Num(senderNum);
			mdto.setUser2Num(receiverNum);
			mdto.setDirection(0L);

		}

		mdto.setMessageFromId(fromId);
		mdto.setSendDate(LocalDateTime.now());
		log.info("mdto" + mdto.toString());

		Message result = messageService.sendMsg(mdto);
		log.info("result.toString() : " + result.toString());
		model.addAttribute("data", new Alert("메시지가 성공적으로 전송되었습니다!", "./" + toId));
		return "message/alert";
	}

	// 쪽지 검색하기
	@GetMapping("/messagebox/{id}/search")
	public String searchByContent(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "type", required = true) String type, @PathVariable String id, String content,
			Model model, @ModelAttribute MessageDto mdto, MsgSearchDto msdto) {
		log.info("들어오니??");
		// log.info("현재 로그인회원번호 : " + session.getAttribute("num"));
		// String messageFromNum = (String) session.getAttribute("num");
		Long num = messageService.findNumById(id); // tester의 userNum = 1
		log.info("사용자 id : " + id);
		log.info("사용자 num : " + num);
		model.addAttribute("mdto", mdto);
		model.addAttribute("fromId", id);
		model.addAttribute("msdto", msdto);

		List<Message> msgList = new ArrayList<>();
		List<MsgListDto> mldtos = new ArrayList<>();
		Long receiverNum = 0L;
		msgList = messageService.findUserMsg(num);
		if (msdto.getType().equals("id")) { // id로 검색할 경우
			if (msgList == null) {
				return "message/msgList";
			}
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
				mldto.setMessageToId(memberService.findOne(receiverNum).getId());
				mldto.setMessageFromId(memberService.findOne(num).getId());
				mldto.setDirection(m.getDirection());
				if (mldto.getMessageFromId().contains(msdto.getKeyword())
						|| mldto.getMessageToId().contains(msdto.getKeyword())) {
					mldtos.add(mldto);
				} else {
					continue;
				}
			}
		} else if (msdto.getType().equals("content")) { // 내용으로 검색
			for (Message m : msgList) {
				if (!m.getContent().contains(msdto.getKeyword())) {
					continue;
				} else {
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
					mldto.setMessageToId(memberService.findOne(receiverNum).getId());
					mldto.setMessageFromId(memberService.findOne(num).getId());
					mldto.setDirection(m.getDirection());
					mldtos.add(mldto);
				}
			}
		} else { // 타입 선택 안할 시 전체 목록 반환
			return "redirect:/messagebox/" + id;
		}

		model.addAttribute("receiverNum", receiverNum);
		model.addAttribute("mldtos", mldtos);
		model.addAttribute("mdto", mdto);
		model.addAttribute("fromNum", num);

		return "message/msgList";
	}
	
	//댓글에서 쪽지보내기
		@PostMapping("community/replymsg")
		@ResponseBody
		public boolean replyMsg(String messageFromId, String messageToId, String content) {
			
			Long messageFromNum = memberService.findNumById(messageFromId);
			Long messageToNum = memberService.findNumById(messageToId);
			
			Long user1Num = 0L;
			Long user2Num = 0L;
			
			if(messageFromNum < messageToNum) {
				 user1Num = messageFromNum;
				 user2Num = messageToNum;
			} else {
				 user1Num = messageToNum;
				 user2Num = messageFromNum;
			}
			
			messageService.replyMsg(Message.createMessage(user1Num, user2Num, content));

			return true;
		}

}