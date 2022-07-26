package com.alice.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alice.project.domain.Friend;
import com.alice.project.domain.FriendsGroup;
import com.alice.project.domain.Member;
import com.alice.project.repository.FriendRepository;
import com.alice.project.repository.FriendsGroupRepository;
import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {

	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;
	private final FriendsGroupRepository fgRepository;

	// 새로운 친구추가 서비스(추가하는 멤버회원 번호, 추가되는 멤버회원의 아이디)
	@SuppressWarnings("unused")
	public boolean addFriendship(Member member, String searchId) {
		Member f = memberRepository.findById(searchId);
		// 만약 그룹 이름이 없으면 기본으로 그룹 생성
		String friendsGroup = "기본그룹";
		if (friendsGroup == null) {
			fgRepository.save(new FriendsGroup(friendsGroup, member.getNum()));
		}
		List<Friend> check = friendRepository.checkAlreadyFriend(member.getNum(), f.getNum());
		Long groupNum = 1L; // 기본그룹에 추가
		if (check.size() <= 0) {
			Friend friend = new Friend(member, f.getNum(), groupNum);
			friendRepository.save(friend);
			return true;
		} else {
		}
		return false;
	}

	// friendService에서 멤버에 있는 num으로 프렌드 객체를 만들기
	public Friend groupNum(Long adderNum, Long addeeNum) {
		return friendRepository.findGroupByAddeeAdderNum(adderNum, addeeNum);
	}

	// adderNum이 추가한 친구목록
	public List<Friend> friendship(Long adderNum) {
		return friendRepository.findByAdderNum(adderNum);
	}

	// 모든 친구관계 조회하는 서비스
	public List<Friend> findAll() {
		return friendRepository.findAll();
	}

	// 회원으로 가입한 친구 검색
	public Member searchMember(String id) {
		return memberRepository.findById(id);
	}

	// 추가된 친구 목록에서 이름 & 아이디로 검색
	public List<Member> searchFriend(String friends, Long adderNum) {
		return memberRepository.findByIdOrName(adderNum, friends);
	}

	// 친구 상세보기(등록된 친구 번호 조회)
	public Friend friendInfo(Long addeeNum) {
		return friendRepository.getById(addeeNum);
	}

	public boolean searchExist(Long adderNum, Long addeeNum) {
		// 존재하지 않음
		if (friendRepository.findGroupByAddeeAdderNum(adderNum, addeeNum) == null) {
			return false;
		}
		// 존재함
		return true;
	}

	// 친구 삭제하기
	public void deleteFriend(Long adderNum, Long addeeNum) {
		friendRepository.deleteById(addeeNum);
	}
}
