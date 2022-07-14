package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.FriendsGroup;
@Repository
public interface FriendsGroupRepository extends JpaRepository<FriendsGroup, Long>  {

	
}
