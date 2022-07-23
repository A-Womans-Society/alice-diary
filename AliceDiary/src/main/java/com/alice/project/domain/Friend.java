package com.alice.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Friend {

   @Id
   @GeneratedValue
   @Column(name = "friend_num")
   private Long num; // 친구 번호
//   private Long adderNum; // 등록하는 친구 회원번호
   private Long addeeNum; // 등록되는 친구 회원번호
   private Long groupNum; // 등록된 그룹 번호 (기본그룹 = 1)

//   @ManyToOne(fetch=FetchType.LAZY)
//   @JoinColumn(name="group_num")
//   private FriendsGroup group; // 친구 소속 그룹
//    
   @ManyToOne(fetch = FetchType.LAZY) // 모든 연관관계는 항상 지연로딩으로 설정(성능상이점)
   @JoinColumn(name = "adder_num")
   private Member member; // 친구 등록하는 회원 객체
//   

   // 연관관계 메서드 (양방향관계)
//   public void setGroup(FriendsGroup group) {
//      this.group = group;
//      group.getFriends().add(this);
//   }
   public void setMember(Member member) {
      this.member = member;
//      return member.getNum();
//      member.getFriends().add(this);
   }

   @Builder
   public Friend(Member member, Long addeeNum, Long groupNum) {
//      this.adderNum = setMember(member);
      setMember(member);
      this.addeeNum = addeeNum;
      this.groupNum = groupNum;
   }
   // 친구 객체 생성 메서드
//   public static Friend createFriend(FriendsGroup group, Member member) {
//      Friend friend = new Friend();
//      friend.setGroup(group);
//      friend.setMember(member);
//      return friend;
//   }
}