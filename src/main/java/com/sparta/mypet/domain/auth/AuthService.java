package com.sparta.mypet.domain.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.mypet.common.entity.GlobalMessage;
import com.sparta.mypet.common.exception.custom.PasswordInvalidException;
import com.sparta.mypet.common.exception.custom.RefreshTokenInvalidException;
import com.sparta.mypet.common.exception.custom.UserStatusNotActiveException;
import com.sparta.mypet.domain.auth.dto.LoginRequestDto;
import com.sparta.mypet.domain.auth.entity.User;
import com.sparta.mypet.domain.auth.entity.UserStatus;
import com.sparta.mypet.security.JwtService;
import com.sparta.mypet.security.TokenType;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final JwtService jwtService;

	@Transactional
	public String login(LoginRequestDto requestDto) {

		User user = userService.findUserByEmail(requestDto.getEmail());
		UserStatus userStatus = user.getStatus();

		if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			throw new PasswordInvalidException(GlobalMessage.PASSWORD_INVALID);
		}

		if (userStatus.equals(UserStatus.WITHDRAWAL)) {
			throw new UserStatusNotActiveException(GlobalMessage.USER_STATUS_WITHDRAWAL);
		} else if (userStatus.equals(UserStatus.SUSPENSION) || userStatus.equals(UserStatus.BAN)) {
			throw new UserStatusNotActiveException(GlobalMessage.USER_STATUS_STOP);
		}

		String accessToken = jwtService.generateToken(TokenType.ACCESS, user.getRole(), user.getEmail());
		String refreshToken = jwtService.generateToken(TokenType.REFRESH, user.getRole(), user.getEmail());

		user.updateRefreshToken(refreshToken);

		jwtService.setRefreshTokenAtCookie(refreshToken);
		jwtService.setHeaderWithAccessToken(accessToken);

		return accessToken;
	}

	@Transactional
	public void logout(String email) {

		User user = userService.findUserByEmail(email);

		user.updateRefreshToken(null);

		jwtService.deleteRefreshTokenAtCookie(); // refresh 토큰 마감시간 0초로 덮어씌우기
		// access 토큰 프론트에서 클리어
	}

	@Transactional
	public String refreshAccessToken(HttpServletRequest request) {

		String refreshToken = jwtService.getRefreshTokenFromRequest(request);
		String email = jwtService.extractEmail(refreshToken);

		User user = userService.findUserByEmail(email);

		if (!user.getRefreshToken().equals(refreshToken)) {
			throw new RefreshTokenInvalidException(GlobalMessage.REFRESH_INVALID);
		}

		Object role = jwtService.extractRole(refreshToken);

		String newAccessToken = jwtService.generateToken(TokenType.ACCESS, role, email);
		String newRefreshToken = jwtService.generateToken(TokenType.REFRESH, role, email);

		user.updateRefreshToken(newRefreshToken);

		jwtService.setRefreshTokenAtCookie(newRefreshToken);
		jwtService.setHeaderWithAccessToken(newAccessToken);

		return newRefreshToken;
	}
}
