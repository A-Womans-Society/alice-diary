package com.alice.project.service;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.alice.project.config.PrincipalDetails;
import com.alice.project.domain.Gender;
import com.alice.project.domain.Member;
import com.alice.project.domain.Status;
import com.alice.project.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
	private final PasswordEncoder passwordEncoder;

    //구글로부터 받은 userRequest 데이터에 대한 후처리가 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //OAuth2 서비스 id 구분코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //OAuth2 로그인 진행 시 키가 되는 필드 값 (PK) (구글의 기본 코드는 "sub")
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        
        String id = registrationId + "_" + oAuth2User.getAttribute("sub");
        String password = passwordEncoder.encode("password");
        String email = oAuth2User.getAttribute("email");
        String status = "ROLE_USER_IN";
        String name = oAuth2User.getAttribute("name");
        String profileImg = "default.png";
        
        Member member = memberRepository.findByEmail(email);
        if(member == null) {
        	System.out.println("처음 가입한 계정입니다.");
        	 member = Member.builder()
        			 .id(id)
        			 .password(password)
        			 .birth(LocalDate.of(1900, 01, 01))
        			 .email(email)
        			 .gender(Gender.UNKNOWN)
        			 .mobile("01000000000")
        			 .name(name)
        			 .regDate(LocalDate.now())
        			 .status(Status.USER_IN)
        			 .build();
        	 Member.setProfileImg(member);
        	 memberRepository.save(member);
        }else {
        	System.out.println("이미 가입한 적이 있습니다.");
        }
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
