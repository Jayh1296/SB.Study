package com.spring.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 현재 ver.6 사용
// security 관련된 설정 (어노테이션 configuration 선언 시 설정 파일로 인식 / context : 설정 파일)
// 모든 URL에 Security를 적용하기 위한 어노테이션 EnableWebSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

//	인터페이스에 설정된 보안을 거치는 필터링 같은 역할
//	bean으로 객체화
//	@Bean
//	SecurityFilterChain filter(HttpSecurity http) throws Exception {
////		모든 페이지에 로그인 하지 않아도 접근하도록 허용
//		http.authorizeHttpRequestsa((authorizeHttpRequests) -> authorizeHttpRequests
//															  .requestMatchers(new AntPathRequestMatcher("/**")).permitAll());
//		
//		return http.build();
//	}
	
//	비밀번호 암호화 정첵을 추가
	@Bean
	PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
//	하드코딩으로 로그인 할 수 있도록 예제
//	@Bean
//	UserDetailsService userDetailsService() {
//		UserDetails user = User.builder().username("user1")
//										 .password(passwordEncoder().encode("1111"))
//										 .roles("USER")
//										 .build();
//		
//		return new InMemoryUserDetailsManager(user);
//	}
	
//	URL로 진입 가능한 특정 경로 설정
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/movie/list", "/member/login", "/member/join").permitAll()
														 .requestMatchers("/css/**", "/jquery/**", "/js/**", "/upload/**", "/display/**").permitAll()  // 정적 파일들 허용
														 .anyRequest().authenticated())
			.formLogin((form) -> form.loginPage("/member/login").usernameParameter("email").permitAll())
			.csrf(csrf -> csrf.disable())
			.logout(logout -> logout.permitAll());
		
		return http.build();
	}
}
