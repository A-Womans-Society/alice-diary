package com.alice.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alice.project.domain.Member;


public interface ProfileRepository extends JpaRepository<Member, Long>{
	
	Member findByNum(Long num);
	
	Optional<Member> findById(String id);
	
	

}
