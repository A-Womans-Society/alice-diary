package com.alice.project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Community;
import com.alice.project.domain.Member;
import com.alice.project.repository.CommunityRepository;
import com.alice.project.repository.MemberRepository;
import com.alice.project.web.CommunityCreateDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

	private final CommunityRepository comRepository;
	private final MemberRepository memberRepository;

	// 커뮤니티 생성하기
	@Transactional
	public Community create(Community com) {
		return comRepository.save(com);
	}

	// 번호로 객체찾기
	public Community findByNum(Long num) {
		return comRepository.findByNum(num);
	}

	// 번호로 커뮤니티이름찾기
	public String findNameByNum(Long num) {
		return comRepository.findNameByNum(num);
	}

	// 번호로 소속회원 찾기
	public String findMemListByNum(Long num) {
		return comRepository.findMemListByNum(num);
	}

	// 커뮤니티 탈퇴하기
	public void resign(Long comNum, String memId) {
		String memberList = comRepository.findMemListByNum(comNum);
		StringBuffer memList = new StringBuffer();
		List<String> ls = new ArrayList<>(Arrays.asList(memberList.split(",")));
		ls.remove(memId);
		
		String newMemList = "";
		if (ls.size() != 0) { // 커뮤니티에 남아있는 사람이 있을 때!
			for (String i : ls) {
				memList.append(i).append(",");
			}
			memList.deleteCharAt(memList.lastIndexOf(","));
			newMemList = memList.toString();
		}

		comRepository.memberListUpdate(comNum, newMemList);
	}

	// 커뮤니티번호로 방장아이디 찾기
	public String findMemberIdByNum(Long num) {
		Long hostMemNum = comRepository.findMemberNumByNum(num);
		String hostMemberId = memberRepository.findIdByNum(hostMemNum);
		return hostMemberId;
	}

	// 커뮤니티 수정하기
	@Transactional
	public void edit(Long comNum, CommunityCreateDto manageCom) {
		comRepository.nameEdit(comNum, manageCom.getComName());
		comRepository.descriptionEdit(comNum, manageCom.getDescription());

	}
	
	@Transactional
	public void deleteCom(Long comNum) {
		comRepository.deleteCom(comNum);
	}

	// 멤버객체로 커뮤니티 객체 찾기
	public List<Community> findByMember(Member member) {
		return comRepository.findByMember(member);
	}

	// 전체 커뮤니티 중 번호랑 멤버리스트(string) 칼럼 2개 가져오기
	public List<Community> getAll() {
		return comRepository.getAll();
	}
	
	//커뮤니티 번호로 설명 가져오기
	public String findDescriptionByNum(Long comNum) {
		return comRepository.findDescriptionByNum(comNum);
	}
}