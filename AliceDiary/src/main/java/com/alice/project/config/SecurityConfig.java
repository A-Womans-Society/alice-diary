package com.alice.project.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.alice.project.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	private final AuthenticationFailureHandler customFailureHandler;
	private final DataSource dataSource; // jpa이라 자동으로 등록되어 있음
	private final CustomOAuth2UserService customOAuth2UserService;

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.formLogin()
				.permitAll()
				.loginPage("/")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/alice")
				.usernameParameter("userid")
				.passwordParameter("password")
				.failureHandler(customFailureHandler)
				
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					 .logoutSuccessUrl("/")
					 .deleteCookies("JSESSIONID")
			.and()
			.oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
			.defaultSuccessUrl("/member/update/{#authentication.name}")
			.userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
			.userService(customOAuth2UserService);//소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
													//리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있음
		

		http.authorizeRequests()
				.mvcMatchers("/css/**", "/font/**", "/js/**", "/img/**").permitAll()
				.mvcMatchers("/","/agree/**", "/register/**", "/login/**", "/check-email-token/**", "/oauth2/**","/**").permitAll()
				.mvcMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().hasAnyRole("ADMIN","USER_IN");
		
		
//      http.exceptionHandling()
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//        ;
		

		
		return http.build();
	}



	@Bean
    public PersistentTokenRepository tokenRepository() {
        // JDBC 기반의 tokenRepository 구현체
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); // dataSource 주입
        return jdbcTokenRepository;
    }
	
	
}