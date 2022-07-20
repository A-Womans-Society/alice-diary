package com.alice.project.service;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alice.project.domain.Member;
import com.alice.project.domain.Suggestion;
import com.alice.project.repository.MemberRepository;
import com.alice.project.repository.SuggestionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true) // 기본적으로 못바꾸게 해놓고
@RequiredArgsConstructor // final 필드 생성자 생성해줌
@Slf4j
public class SuggestionService {
	private final SuggestionRepository suggestionRepository;
	
	@Transactional
	public Suggestion saveSuggest(Suggestion suggestion) {
		return suggestionRepository.save(suggestion); // insert
	}
}
