package com.alice.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alice.project.domain.AttachedFile;
import com.alice.project.domain.Community;
import com.alice.project.domain.Friend;
import com.alice.project.domain.Member;
import com.alice.project.domain.Post;
import com.alice.project.service.AttachedFileService;
import com.alice.project.service.CommunityService;
import com.alice.project.service.FriendService;
import com.alice.project.service.FriendsGroupService;
import com.alice.project.service.MemberService;
import com.alice.project.service.PostService;
import com.alice.project.service.ReplyService;
import com.alice.project.web.CommunityCreateDto;
import com.alice.project.web.FriendshipDto;
import com.alice.project.web.PostSearchDto;
import com.alice.project.web.ReplyDto;
import com.alice.project.web.WriteFormDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommunityController {

	private final MemberService memberService;
	private final FriendService friendService;
	private final FriendsGroupService friendsGroupService;
	private final CommunityService communityService;
	private final PostService postService;
	private final AttachedFileService attachedFileService;
	private final ReplyService replyService;

	@GetMapping("/community/create")
	public String createForm(@AuthenticationPrincipal UserDetails user, Model model) {
		log.info("get 도착");
		model.addAttribute("ccdto", new CommunityCreateDto());
		model.addAttribute("member", memberService.findById(user.getUsername()));
		return "community/createCommunity";
	}

	// 친구 검색
	@PostMapping("/community/searchFriend")
	@ResponseBody
	public List<FriendshipDto> searchFriend(String searchFriend, @AuthenticationPrincipal UserDetails user) {
		Long adderNum = memberService.findById(user.getUsername()).getNum();
		List<Member> myFriends = friendService.searchFriend(searchFriend, adderNum);
		List<FriendshipDto> searchFriendList = new ArrayList<FriendshipDto>();
		for (Member f : myFriends) {
			Member sf = memberService.findByNum(f.getNum());
			Friend fg = friendService.groupNum(adderNum, f.getNum());
			String groupName = friendsGroupService.getGroupName(fg.getGroupNum());
			log.info("그룹이름:" + groupName);
			FriendshipDto dto = new FriendshipDto(sf.getId(), sf.getName(), groupName);
			searchFriendList.add(dto);
		}
		return searchFriendList;
	}

	// 커뮤니티 생성하기
	@PostMapping("/community/create")
	public String communityCreate(String[] selected, String comName, String description,
			@AuthenticationPrincipal UserDetails user) {
		log.info("post 도착");
		String comMembers = "";

		for (int i = 0; i < selected.length; i++) {
			log.info(selected[i]);
			comMembers += selected[i];
			if (i < selected.length - 1) {
				comMembers += ",";
			}
		}
		Member member = memberService.findById(user.getUsername());

		Community com = Community.createCommunity(comMembers, comName, description, member);

		communityService.create(com);

		String comNum = Long.toString(com.getNum());

		return "redirect:./" + comNum + "/list";
	}

	/*
	 * // 친구 프로필 상세보기
	 * 
	 * @GetMapping("/community/friendInfo/{id}") public Member
	 * friendInfo(@PathVariable("id") String id) { Member member =
	 * profileService.findById(id); log.info("member=" + member);
	 * 
	 * return member; }
	 */

	// 게시글 리스트 가져오기
	@GetMapping("community/{comNum}/list")
	public String list(@PathVariable Long comNum, Model model,
			@ModelAttribute("postSearchDto") PostSearchDto postSearchDto, @AuthenticationPrincipal UserDetails user,
			@PageableDefault(page = 0, size = 5, direction = Sort.Direction.DESC) Pageable pageable) {

		log.info("컨트롤러 로그 postSearchDto :" + postSearchDto.toString());

		String keyword = postSearchDto.getKeyword();
		Long size = 0L;
		Page<Post> list = null;

		if (keyword == null) {
			list = postService.comList(comNum, pageable);
			size = list.getTotalElements();
		} else {
			list = postService.comSearchList(comNum, postSearchDto, pageable);
			size = list.getTotalElements();
		}

		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 2, 1);
		int endPage = 0;
		if (startPage == 1) {
			if (list.getTotalPages() < 5) {
				endPage = list.getTotalPages();
			} else {
				endPage = 5;
			}
		} else {
			endPage = Math.min(nowPage + 2, list.getTotalPages());
		}

		if (endPage == list.getTotalPages() && (endPage - startPage) < 5) {
			startPage = (endPage - 4 <= 0) ? 1 : endPage - 4;
		}

		model.addAttribute("comName", communityService.findNameByNum(comNum));
		model.addAttribute("list", list);
		model.addAttribute("comNum", comNum);
		model.addAttribute("size", size);
		log.info("size : " + size);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", postSearchDto.getType());
		model.addAttribute("member", memberService.findById(user.getUsername()));

		log.info("nowPage:" + nowPage);
		log.info("startPage:" + startPage);
		log.info("endPage:" + endPage);

		return "community/communityList";
	}

	// 글쓰기
	@GetMapping("community/{comNum}/post")
	public String writeform(@PathVariable Long comNum, Model model, @AuthenticationPrincipal UserDetails user) {

		model.addAttribute("writeFormDto", new WriteFormDto());
		model.addAttribute("comNum", comNum);
		model.addAttribute("member", memberService.findById(user.getUsername()));
		return "community/comWriteForm";
	}

	// 글쓰기
	@PostMapping("community/{comNum}/post")
	public String writeSubmit(@PathVariable Long comNum, WriteFormDto writeFormDto, HttpSession session,
			@AuthenticationPrincipal UserDetails user) {

		Member member = memberService.findById(user.getUsername());
		Community community = communityService.findByNum(comNum);

		Post post = Post.creatCommunity(writeFormDto, member, community);
		Post writedPost = postService.write(post);

		attachedFileService.postFileUpload(writeFormDto.getOriginName(), writedPost, session, user.getUsername());

		return "redirect:./list";
	}
	
	// 커뮤니티게시글 삭제하기
	@RequestMapping("/community/delete")
	public String comPostDelete(Long comNum,Long num) {
		log.info("컨트롤러 실행 num:" + num);

		postService.deletePostwithReply(num);
		postService.deletePostwithFile(num);
		postService.deletePost(num);

		return "redirect:./" + comNum + "/list";
	}


	// 게시글 상세보기
	@GetMapping("community/{comNum}/get/{num}")
	public String postView(@PathVariable Long comNum, Model model, @PathVariable Long num, Pageable pageable, HttpSession session,
			@AuthenticationPrincipal UserDetails user) {

		log.info("num :" + num);
		log.info("user.getUsername() :" + user.getUsername());
		Post viewPost = postService.postView(num);

		postService.viewCntUp(num);

		model.addAttribute("postView", viewPost);

		List<AttachedFile> files = attachedFileService.fileView(viewPost, pageable);
		model.addAttribute("files", files);

		List<ReplyDto> replyList = replyService.replyList(num);

		model.addAttribute("replyList", replyList);
		model.addAttribute("member", memberService.findById(user.getUsername()));
		model.addAttribute("comNum", comNum);

		return "community/comPostView";
	}
	
	// get 게시글 수정하기 첨부파일도 수정
	@GetMapping("/community/{comNum}/put/{num}")
	public String getUpdate(@PathVariable Long comNum,@PathVariable Long num, Model model, Pageable pageable, @AuthenticationPrincipal UserDetails user) {
		log.info("수정컨트롤러 get");

		Post getUpdate = postService.postView(num);

		WriteFormDto updateDto = new WriteFormDto(num, getUpdate.getTitle(), getUpdate.getContent());
		List<AttachedFile> files = attachedFileService.fileView(getUpdate, pageable);

		model.addAttribute("files", files);
		model.addAttribute("updateDto", updateDto);
		model.addAttribute("member", memberService.findById(user.getUsername()));
		model.addAttribute("comNum", comNum);
		model.addAttribute("num", num); //해당 게시글 번호
		return "community/comUpdateForm";
	}
	
	// post 게시글 수정하기 첨부파일도 수정
	@PostMapping("/community/{comNum}/put/{num}")
	public String updatePorc(@PathVariable Long comNum, @PathVariable Long num,WriteFormDto updateDto, HttpSession session, @AuthenticationPrincipal UserDetails user) {

		String postNum = Long.toString(updateDto.getPostNum());

		postService.updatePost(updateDto.getPostNum(), updateDto);

		Post updatedPost = postService.findOne(updateDto.getPostNum());

		attachedFileService.postFileUpload(updateDto.getOriginName(), updatedPost, session, user.getUsername());

		return "redirect:/community/"+comNum+"/get/" + num;
	}
	
	// 게시글 수정에서 파일하나 삭제하기
		@PostMapping("/community/put/filedelete")
		@ResponseBody
		public JSONObject oneFileDelete(Long num, Long postNum) {
			
			postService.deleteOneFile(num);

			JSONObject jObj = new JSONObject();

			List<AttachedFile> files = attachedFileService.fileDeleteAfterList(postNum);

			jObj.put("files", files);

			return jObj;
		}

}
