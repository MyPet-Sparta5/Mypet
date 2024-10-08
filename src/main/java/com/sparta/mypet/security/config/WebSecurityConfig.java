package com.sparta.mypet.security.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sparta.mypet.security.JwtService;
import com.sparta.mypet.security.UserDetailsServiceImpl;
import com.sparta.mypet.security.filter.JwtAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;
	private final JwtService jwtService;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtService, userDetailsService);
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable());

		http.sessionManagement(
			sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// CORS 설정 추가
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(
				PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
			.requestMatchers(HttpMethod.POST, "/api/users").permitAll() // 회원가입 요청 허가
			.requestMatchers(HttpMethod.GET, "/api/users/social-account/**").permitAll()
			.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll() // 로그인 요청 허가
			.requestMatchers(HttpMethod.POST, "/api/auth/send-verification").permitAll() // 이메일 코드 발송
			.requestMatchers(HttpMethod.POST, "/api/auth/verify").permitAll() // 이메일 코드 인증
			.requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll() // 토큰 재발급 요청 허가
			.requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
			.requestMatchers(HttpMethod.GET, "/api/facilities/**").permitAll()
			.requestMatchers("/api/admin/user-manage/*/role").hasAuthority("ROLE_ADMIN")
			.requestMatchers("/api/admin/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
			.requestMatchers("/api/oauth/kakao").permitAll()
			.requestMatchers("/api/oauth/google").permitAll()
			.anyRequest().authenticated() // 그 외 모든 요청 인증처리
		);

		http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(
			List.of("http://localhost:3000", "https://www.petzoa.site", "https://api.petzoa.site")); // 리액트 앱의 주소
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);
		configuration.addExposedHeader("Authorization");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
