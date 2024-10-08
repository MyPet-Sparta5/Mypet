package com.sparta.mypet.domain.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.mypet.common.dto.DataResponseDto;
import com.sparta.mypet.common.dto.MessageResponseDto;
import com.sparta.mypet.common.entity.GlobalMessage;
import com.sparta.mypet.common.util.ResponseFactory;
import com.sparta.mypet.domain.auth.dto.LoginRequestDto;
import com.sparta.mypet.domain.auth.dto.LoginResponseDto;
import com.sparta.mypet.security.UserDetailsImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/auth/login")
	public ResponseEntity<DataResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto requestDto) {

		LoginResponseDto responseDto = authService.login(requestDto);

		return ResponseFactory.ok(responseDto, GlobalMessage.LOGIN_SUCCESS.getMessage());
	}

	@PostMapping("/auth/logout")
	public ResponseEntity<MessageResponseDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {

		authService.logout(userDetails.getUsername());

		return ResponseFactory.ok(GlobalMessage.LOGOUT_SUCCESS.getMessage());
	}

	@PostMapping("/auth/refresh")
	public ResponseEntity<MessageResponseDto> refreshAccessToken(HttpServletRequest request) {

		authService.refreshAccessToken(request);

		return ResponseFactory.ok(GlobalMessage.REFRESH_TOKEN_SUCCESS.getMessage());
	}
}
