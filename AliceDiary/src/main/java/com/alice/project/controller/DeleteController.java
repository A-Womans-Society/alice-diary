package com.alice.project.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.AttachedFile;
import com.alice.project.service.AttachedFileService;
import com.alice.project.service.DeleteService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/AliceDiary")
@Slf4j
public class DeleteController {
	
	@Autowired
	private DeleteService deleteService;
	
	@Autowired
	private AttachedFileService attachedFileService;
	
	
	@RequestMapping("/community/delete")
	public String postDelete(Long num) {
		log.info("컨트롤러 실행 num:"+num);
		
		deleteService.deletePostwithReply(num);
		
		deleteService.deletePostwithFile(num);			
			
		deleteService.deletePost(num);	
		
		
		
		return "redirect:list";
	}
	
	@PostMapping("/community/put/filedelete")
	@ResponseBody
	public JSONObject oneFileDelete(Long num, Long postNum) {
		log.info("!!!!!!! file num : " + num);
		deleteService.deleteOneFile(num);
		
		JSONObject jObj = new JSONObject();
		
		List<AttachedFile> files = attachedFileService.newFileView(postNum);
		
		jObj.put("files", files);
		
		return jObj;
		
	}

}
