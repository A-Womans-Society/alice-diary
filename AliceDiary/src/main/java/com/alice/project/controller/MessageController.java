package com.alice.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Message;
import com.alice.project.handler.Alert;
import com.alice.project.service.MemberService;
import com.alice.project.service.MessageService;
import com.alice.project.web.MessageDto;
import com.alice.project.web.MessageListDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;
	private final MemberService memberService;
	
	// 메세지 목록
	@GetMapping(value = "/messagebox/{memberNum}")
	public String messageList(@PathVariable("memberNum") Long num, Model model,
			@ModelAttribute MessageDto mdto) {
		//log.info("현재 로그인회원번호 : " + session.getAttribute("num"));
		// String messageFromNum = (String) session.getAttribute("num");
		if (messageService.findLiveReceiverNumsBySenderNum(num).isEmpty()) {
			return "message/msgListNull";
		}
		// dto리스트 준비
		List<MessageListDto> mldtos = new ArrayList<>();
		// 받는 사람 번호 목록 (2, 3)
		List<Long> nums = messageService.findLiveReceiverNumsBySenderNum(num);
		for (Long n : nums) {
			log.info("nums 중 하나 : " + n);
		}
		
		for (Long n : nums) {
			Message msg = messageService.findRecentMsgs(num, n);
			MessageListDto mldto = new MessageListDto();
			mldto.setMessageFromNum(num);
			mldto.setMessageToNum(n);
			mldto.setSendDate(msg.getSendDate());
			mldto.setRecentContent(msg.getContent());
			log.info("MC의 memberService.findOne(n).getId() : " + memberService.findOne(n).getId());
			mldto.setMessageToId(memberService.findOne(n).getId());
			mldtos.add(mldto);
		}
		
		String fromId = messageService.findIdByNum(num);

		model.addAttribute("fromId", fromId);
		model.addAttribute("mldtos", mldtos);
		model.addAttribute("mdto", mdto);

		return "message/msgList";
	}
	
	// 쪽지함 삭제
	@DeleteMapping("/messagebox/{memberNum}/{messageToNum}")
	@ResponseBody
	public String deleteMessage(@PathVariable("memberNum") Long num, 
			@PathVariable("messageToNum") Long toNum, Model model) {
		messageService.cutMsgRelations(num, toNum);
		return "1";
	}

	// 개별 쪽지함 상세보기
	@GetMapping("/messagebox/{memberNum}/{messageToNum}")
	public String showMessages(@PathVariable("memberNum") Long num, 
			@PathVariable("messageToNum") Long toNum, Model model, 
			HttpSession session) {
		List<Message> msgs = messageService.findLiveMsgs(num, toNum);
		List<MessageDto> mdtos = new ArrayList<>();
		for (Message msg : msgs) {
			MessageDto mdto = new MessageDto(msg, messageService);
			log.info("MC의 mdto.getContent() : " + mdto.getContent());
			mdtos.add(mdto);
		}
		model.addAttribute("mdtos", mdtos);
		
		String toId = messageService.findIdByNum(toNum);
		String fromId = messageService.findIdByNum(num);
//		String userNum = session.getAttribute("userNum").toString();
		// 세션에서 사용자번호 받아서 넣기
//		model.addAttribute("userNum", 3); // ***테스트용으로 하드코딩한 것
		model.addAttribute("userNum", num);
		model.addAttribute("toNum", toNum);
		model.addAttribute("toId", toId);
		model.addAttribute("fromId", fromId);
		return "message/msgDetail";
	}
	
	// 쪽지함 내에서 쪽지보내기
	@PostMapping("/messagebox/{memberNum}/{messageToNum}")
	public String sendMessages(@PathVariable("memberNum") Long num, 
			@PathVariable("messageToNum") Long toNum, Model model, 
			HttpSession session, @ModelAttribute MessageDto mdto) {
		mdto.setMessageFromNum(num);
		mdto.setMessageToNum(toNum);
		log.info("mdto.getContent()" + mdto.getContent());
		Message result = messageService.sendMsg(mdto);
		log.info("result.toString() : " + result.toString());
		model.addAttribute("data", new Alert("메시지가 성공적으로 전송되었습니다!", "./" + toNum));
		return "message/alert";
	}
	
	// 쪽지함 목록에서 바로 쪽지보내기
	@PostMapping("/messagebox/{memberNum}")
	public String sendMessage(@PathVariable("memberNum") Long num, Model model, 
			HttpSession session, @ModelAttribute MessageDto mdto) {
		Long toNum = messageService.findNumById(mdto.getMessageToId());
		mdto.setMessageToNum(toNum);
		mdto.setMessageFromNum(num);
		mdto.setMessageFromId(messageService.findIdByNum(num));
		mdto.setSendDate(LocalDateTime.now());
		log.info("mdto" + mdto.toString());
		
		Message result = messageService.sendMsg(mdto);
		log.info("result.toString() : " + result.toString());
		model.addAttribute("data", new Alert("메시지가 성공적으로 전송되었습니다!", "./" + num));
		return "message/alert";
	}

	
	
}
