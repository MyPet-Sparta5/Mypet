package com.sparta.mypet.common.exception;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sparta.mypet.common.dto.MessageResponseDto;
import com.sparta.mypet.common.entity.GlobalMessage;
import com.sparta.mypet.common.exception.custom.LikeNotFoundException;
import com.sparta.mypet.common.exception.custom.PasswordInvalidException;
import com.sparta.mypet.common.exception.custom.PostNotFoundException;
import com.sparta.mypet.common.exception.custom.RefreshTokenInvalidException;
import com.sparta.mypet.common.exception.custom.ReportDuplicationException;
import com.sparta.mypet.common.exception.custom.SocialAccountLinkedException;
import com.sparta.mypet.common.exception.custom.UserEmailDuplicateException;
import com.sparta.mypet.common.exception.custom.UserMisMatchException;
import com.sparta.mypet.common.exception.custom.UserNicknameDuplicateException;
import com.sparta.mypet.common.exception.custom.UserNotFoundException;
import com.sparta.mypet.common.exception.custom.UserPasswordDuplicationException;
import com.sparta.mypet.common.exception.custom.UserStatusNotActiveException;
import com.sparta.mypet.common.util.ResponseFactory;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * validation exception handler : valid 에러 메세지 클라이언트에 전달
	 *
	 * @param e : valid 에러 캐치
	 * @return 에러 메세지 응답
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MessageResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		String errorMessages = e.getBindingResult()
			.getAllErrors()
			.stream()
			.map(ObjectError::getDefaultMessage)
			.collect(Collectors.joining(", "));

		return ResponseFactory.badRequest(errorMessages);
	}

	/**
	 * DB exception handler : DB 저장 에러 메세지 클라이언트에 전달
	 *
	 * @param e : DB 저장 에러 캐치
	 * @return 에러 메세지 응답
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<MessageResponseDto> handleConstraintViolationException(ConstraintViolationException e) {

		StringBuilder errorMessages = new StringBuilder();

		e.getConstraintViolations()
			.forEach(violation -> errorMessages.append(violation.getPropertyPath())
				.append(": ")
				.append(violation.getMessage())
				.append("\n"));

		return ResponseFactory.badRequest(errorMessages.toString());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<MessageResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.badRequest(errorMessage);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<MessageResponseDto> handleUsernameNotFoundException(UsernameNotFoundException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.notFound(errorMessage);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<MessageResponseDto> handleUserNotFoundException(UserNotFoundException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.notFound(errorMessage);
	}

	@ExceptionHandler(UserEmailDuplicateException.class)
	public ResponseEntity<MessageResponseDto> handleUserEmailDuplicateException(UserEmailDuplicateException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.conflictError(errorMessage);
	}

	@ExceptionHandler(PasswordInvalidException.class)
	public ResponseEntity<MessageResponseDto> handlePasswordInvalidException(PasswordInvalidException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.authorizedError(errorMessage);
	}

	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<MessageResponseDto> handlePostNotFoundException(PostNotFoundException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.notFound(errorMessage);
	}

	@ExceptionHandler(UserMisMatchException.class)
	public ResponseEntity<MessageResponseDto> handleUserMisMatchException(UserMisMatchException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.notFound(errorMessage);
	}

	@ExceptionHandler(LikeNotFoundException.class)
	public ResponseEntity<MessageResponseDto> handleLikeNotFoundException(LikeNotFoundException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.notFound(errorMessage);
	}

	@ExceptionHandler(RefreshTokenInvalidException.class)
	public ResponseEntity<MessageResponseDto> handleRefreshTokenInvalidException(RefreshTokenInvalidException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.authorizedError(errorMessage);
	}

	@ExceptionHandler(UserStatusNotActiveException.class)
	public ResponseEntity<MessageResponseDto> handleUserStatusNotActiveException(UserStatusNotActiveException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.authorizedError(errorMessage);
	}

	@ExceptionHandler(UserNicknameDuplicateException.class)
	public ResponseEntity<MessageResponseDto> handleUserNicknameDuplicateException(UserNicknameDuplicateException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.conflictError(errorMessage);
	}

	@ExceptionHandler(UserPasswordDuplicationException.class)
	public ResponseEntity<MessageResponseDto> handleUserPasswordDuplicationException(
		UserPasswordDuplicationException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.conflictError(errorMessage);
	}

	@ExceptionHandler(SocialAccountLinkedException.class)
	public ResponseEntity<MessageResponseDto> handleSocialAccountLinkedException(SocialAccountLinkedException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.badRequest(errorMessage);
	}

	@ExceptionHandler(ReportDuplicationException.class)
	public ResponseEntity<MessageResponseDto> handleReportDuplicationException(ReportDuplicationException e) {
		String errorMessage = GlobalMessage.ERROR_MESSAGE_PREFIX.getMessage() + e.getMessage();

		return ResponseFactory.conflictError(errorMessage);
	}
}
