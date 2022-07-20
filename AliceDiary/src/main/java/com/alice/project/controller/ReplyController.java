package com.alice.project.controller;

import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.Reply;
import com.alice.project.service.ReplyService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ReplyController {

	@Autowired
	private ReplyService replyService;

	@PostMapping("/community/reply")
	@ResponseBody
	public JSONObject replyWrite(String memberId, Long postNum, String content) {
		log.info(memberId, postNum, content);
		Reply newReply = replyService.replyWrite(memberId, postNum, content);

		JSONObject jObj = new JSONObject();

		jObj.put("replyNum", newReply.getNum());
		jObj.put("id", newReply.getMember().getId());
		jObj.put("repDate", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(newReply.getRepDate()));
		jObj.put("repContent", newReply.getContent());
		jObj.put("postNum", newReply.getPost().getNum());
		

		return jObj;

	}

	@PostMapping("/community/replyreply")
	@ResponseBody
	public JSONObject replyReplyWrite(String memberId, Long postNum, Long parentRepNum, String content) {
		log.info(memberId, postNum, parentRepNum, content);
		Reply newReplyReply = replyService.replyReplyWrite(memberId, postNum, parentRepNum, content);

		JSONObject jObj = new JSONObject();

		jObj.put("id", newReplyReply.getMember().getId());
		jObj.put("repDate", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(newReplyReply.getRepDate()));
		jObj.put("repContent", newReplyReply.getContent());

		return jObj;

	}
}
