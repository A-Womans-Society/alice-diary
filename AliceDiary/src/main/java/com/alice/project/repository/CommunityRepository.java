package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Community;

@Repository
@Transactional(readOnly = true)
public interface CommunityRepository extends JpaRepository<Community, Long>{
	
	Community findByNum(Long num);
	
	@Query(value="select name from community where community_num = :num", nativeQuery=true)
	String findNameByNum(Long num);

}
