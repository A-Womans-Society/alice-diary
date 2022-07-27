package com.alice.project.web;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.alice.project.domain.Gender;
import com.alice.project.domain.Member;
import com.alice.project.domain.Status;
import com.alice.project.service.CustomOAuth2UserService;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuthAttributes {

	private final PasswordEncoder passwordEncoder;
	private final CustomOAuth2UserService customOAuth2UserService;
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String profileImg;
	private Status status;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email,
			String profileImg) {
		this.passwordEncoder = null;
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.profileImg = profileImg;
		this.customOAuth2UserService = null;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
			Map<String, Object> attributes) {
		return OAuthAttributes.builder().name((String) attributes.get("name")).email((String) attributes.get("email"))
				.profileImg((String) attributes.get("profileImg")).attributes(attributes)
				.nameAttributeKey(userNameAttributeName).build();
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder().name((String) attributes.get("name")).email((String) attributes.get("email"))
				.profileImg((String) attributes.get("profileImg")).attributes(attributes)
				.nameAttributeKey(userNameAttributeName).build();
	}

	public Member toEntity() {
		return Member.builder().id("google_" + name)
//                .password(passwordEncoder.encode("password"))
				.birth(LocalDate.of(1900, 01, 01)).email(email).gender(Gender.UNKNOWN).mobile("01000000000").name(name)
				.profileImg("default.png").regDate(LocalDate.now()).status(Status.USER_IN).build();
	}
}