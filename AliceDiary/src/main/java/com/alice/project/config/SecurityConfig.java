package com.alice.project.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.alice.project.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
	
	@Autowired
	MemberService memberService;
	
	private final AuthenticationFailureHandler customFailureHandler;
	
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
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/");
//        http.csrf().disable();

		http.authorizeRequests()
				.mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
				.mvcMatchers("/", "/login/**").permitAll()
				.mvcMatchers("/alice/**", "/messagebox/**", "/member/**", "/friends/**", "/community/**").hasAuthority("ROLE_USER_IN")
				.mvcMatchers("/admin/**").hasAuthority("ROLE_ADMIN");
		
//        http.exceptionHandling()
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//        ;

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}