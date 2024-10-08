package com.sparta.mypet.common.entity;

import lombok.Getter;

@Getter
public enum GlobalMessage {

	// 공통 메시지
	MSG_OK("요청이 성공적으로 완료되었습니다."),
	MSG_CREATED("요청에 대한 데이터가 생성 되었습니다"),
	MSG_BAD_REQUEST("해당 요청을 처리할 수 없습니다."),
	MSG_NOT_FOUND("요청한 리소스를 찾을 수 없습니다."),
	MSG_INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다."),
	MSG_CONFLICT("이미 존재하는 항목입니다."),
	MSG_UNAUTHORIZED("요청에 대해 인증을 실패했습니다."),
	ERROR_MESSAGE_PREFIX("Exception caught: "),
	MSG_FOUND("요청한 리소스의 URI가 일시적으로 변경되었습니다."),

	// 응답 메시지
	// User
	CREATE_USER_SUCCESS("회원가입이 성공적으로 완료되었습니다."),
	LOGIN_SUCCESS("로그인이 성공적으로 완료되었습니다."),
	LOGOUT_SUCCESS("로그아웃이 성공적으로 완료되었습니다."),
	REFRESH_TOKEN_SUCCESS("토큰 재발급이 성공적으로 완료되었습니다."),
	GET_USER_SUCCESS("회원 조회가 성공적으로 완료되었습니다."),
	WITHDRAW_USER_SUCCESS("회원 탈퇴가 성공적으로 완료되었습니다."),
	UPDATE_USER_SUCCESS("회원 정보 수정이 성공적으로 완료되었습니다."),
	UPDATE_USER_PASSWORD_SUCCESS("회원 비밀번호 변경이 성공적으로 완료되었습니다."),

	// Report
	CREATE_REPORT_SUCCESS("게시물 신고가 성공적으로 완료되었습니다."),
	REPORT_STATUS_DUPLICATE("현재 신고 상태와 같습니다."),

	// 에러 메세지
	// User
	USER_EMAIL_NOT_FOUND("존재하지 않는 이메일입니다."),
	USER_EMAIL_DUPLICATE("이미 존재하는 이메일입니다."),
	PASSWORD_INVALID("비밀번호가 일치하지 않습니다."),
	REPEAT_PASSWORD_INVALID("반복 비밀번호가 일치하지 않습니다."),
	USER_NOT_FOUND("존재하지 않는 유저입니다."),
	REFRESH_INVALID("검증되지 않은 Refresh 토큰입니다."),
	USER_STATUS_WITHDRAWAL("해당 유저는 탈퇴 상태입니다."),
	USER_STATUS_STOP("해당 유저는 정지 상태입니다."),
	USER_NICKNAME_DUPLICATE("현재 닉네임과 같습니다."),
	USER_PASSWORD_DUPLICATE("현재 비밀번호와 같습니다."),
	EMAIL_ALREADY_USED("해당 이메일로 가입된 계정이 있습니다. 로그인 후 마이페이지에서 연동해주세요."),
	USER_STATUS_DUPLICATE("현재 상태와 같습니다."),
	USER_ROLE_DUPLICATE("현재 역할와 같습니다."),
	SOCIAL_LINKED("소셜 계정이 연결되어있습니다."),
	USER_REACTIVE_FAIL("해당 유저 정상화에 실패했습니다."),

	// Post
	POST_NOT_FOUND("존재하지 않는 게시물입니다."),
	NOT_AUTHORITY_OWNER("게시물 접근 권한이 없습니다."),
	INVALID_ENUM_CATEGORY("존재하지 않는 카테고리입니다."),
	INVALID_ENUM_POST_STATUS("존재하지 않는 포스트 상태입니다."),
	POST_STATUS_DUPLICATE("현재 포스트 상태와 같습니다."),

	// File
	UPLOAD_FILE_NOT_FOUND("업로드 하려는 파일이 없습니다"),
	MAX_FILE_COUNT_EXCEEDED("파일을 5개 이상 업로드할 수 없습니다."),
	PROCESSING_FILE_FAILED("업로드를 실패했습니다"),
	INVALID_TYPE_FILE("지원하지 않는 타입의 파일입니다"),
	INVALID_SIZE_IMAGE("image파일은 최대 10MB까지 업로드 가능합니다."),
	INVALID_SIZE_VIDEO("vidoe파일은 최대 200MB까지 업로드 가능합니다."),

	// Comment
	COMMENT_NOT_FOUND("존재하지 않는 댓글입니다."),
	NOT_COMMENT_OWNER("댓글 작성자가 아닙니다."),

	// Like
	LIKE_ALREADY_EXISTS("이미 좋아요를 누른 게시물입니다."),
	LIKE_NOT_FOUND("해당 포스트에 좋아요를 하지않았습니다."),

	// Report
	SELF_REPORT_NOT("자신의 게시물을 신고할 수 없습니다."),
	REPORT_NOT_FOUND("존재하지 않는 신고입니다."),
	REPORT_DUPLICATE("이미 신고한 게시물입니다."),

	// JSON
	JSON_PARSING_ERROR("JSON 파싱에 실패했습니다."),

	// SOCIAL
	KAKAO_SERVER_ERROR("카카오 서버 오류 발생 : "),
	SOCIAL_NOT_LINKED_ERROR("해당 소셜 계정에 연동되어있지 않습니다."),
	SOCIAL_ALREADY_LINKED("해당 소셜 계정이 이미 연동되어있습니다."),
	GOOGLE_SERVER_ERROR("구글 서버 오류 발생 : "),

	TOO_MANY_REQUESTS("잠시 후에 잠시 시도해주세요."),

	// REDIS
	REDIS_VALUE_ERROR("Redis 캐시에 해당 키값이 존재하지 않습니다.");

	// Valid
	public static final String COMMENT_CONTENT_NOT_BLANK_MESSAGE = "댓글의 내용을 작성해야합니다.";

	private final String message;

	GlobalMessage(String message) {
		this.message = message;
	}
}
