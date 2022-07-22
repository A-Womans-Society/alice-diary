package com.alice.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alice.project.domain.Member;
import com.alice.project.domain.Suggestion;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long>{

//	Suggestion save(Suggestion suggestion);

}
