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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	private final AuthenticationFailureHandler customFailureHandler;
	private final DataSource dataSource; // jpa이라 자동으로 등록되어 있음

	
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
					 .deleteCookies("JSESSIONID");
					 

		http.authorizeRequests()
				.mvcMatchers("/css/**", "/font/**", "/js/**", "/img/**").permitAll()
				.mvcMatchers("/", "/login/**").permitAll()
				.mvcMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().hasAnyRole("ADMIN","USER_IN");
		
		
//        http.exceptionHandling()
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