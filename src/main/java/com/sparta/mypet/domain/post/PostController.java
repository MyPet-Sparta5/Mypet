package com.sparta.mypet.domain.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.mypet.common.dto.DataResponseDto;
import com.sparta.mypet.common.util.ResponseFactory;
import com.sparta.mypet.domain.post.dto.PostListResponseDto;
import com.sparta.mypet.domain.post.dto.PostRequestDto;
import com.sparta.mypet.domain.post.dto.PostResponseDto;
import com.sparta.mypet.domain.post.dto.PostSearchCondition;
import com.sparta.mypet.security.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/posts")
	public ResponseEntity<DataResponseDto<PostResponseDto>> createPost(
		@Valid @RequestPart PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestParam("category") String category,
		@RequestPart(value = "files", required = false) List<MultipartFile> files) {

		PostResponseDto responseDto = postService.createPost(userDetails.getUsername(), requestDto, category, files);
		return ResponseFactory.created(responseDto, "게시물 생성 성공");
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<DataResponseDto<PostResponseDto>> updatePost(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@Valid @RequestBody PostRequestDto requestDto, @PathVariable Long postId) {
		PostResponseDto responseDto = postService.updatePost(userDetails.getUsername(), requestDto, postId);
		return ResponseFactory.ok(responseDto, "게시물 수정 성공");
	}

	@PutMapping("/posts/{postId}/delete")
	public ResponseEntity<DataResponseDto<PostResponseDto>> deletePost(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long postId) {
		PostResponseDto responseDto = postService.deletePost(userDetails.getUsername(), postId);
		return ResponseFactory.ok(responseDto, "게시물 삭제 성공");
	}

	@GetMapping("/posts")
	public ResponseEntity<DataResponseDto<Page<PostListResponseDto>>> getPosts(
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int pageSize,
		@RequestParam(defaultValue = "createdAt, desc") String sortBy,
		PostSearchCondition condition) {
		Page<PostListResponseDto> responseDtoList = postService.getPosts(page, pageSize, sortBy, condition);
		return ResponseFactory.ok(responseDtoList, "게시물 전체 조회 성공");
	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<DataResponseDto<PostResponseDto>> getPost(@PathVariable Long postId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		String email = (userDetails != null) ? userDetails.getUsername() : "";

		PostResponseDto responseDto = postService.getPost(postId, email);
		return ResponseFactory.ok(responseDto, "게시물 단건 조회 성공");
	}
}