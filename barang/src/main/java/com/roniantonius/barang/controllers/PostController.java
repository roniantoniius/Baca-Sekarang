package com.roniantonius.barang.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.barang.domain.CreatePostRequest;
import com.roniantonius.barang.domain.UpdatePostRequest;
import com.roniantonius.barang.domain.dtos.CreatePostRequestDto;
import com.roniantonius.barang.domain.dtos.PostDto;
import com.roniantonius.barang.domain.dtos.UpdatePostRequestDto;
import com.roniantonius.barang.domain.entities.Post;
import com.roniantonius.barang.domain.entities.User;
import com.roniantonius.barang.mappers.PostMapper;
import com.roniantonius.barang.services.PostService;
import com.roniantonius.barang.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	private final PostMapper postMapper;
	private final UserService userService;
	
	@GetMapping
	public ResponseEntity<List<PostDto>> getAllPosts(
			@RequestParam(required = false) UUID categoryId,
			@RequestParam(required = false) UUID tagId
			){
		List<Post> posts = postService.getAllPosts(categoryId, tagId);
		List<PostDto> postDtos = posts.stream().map(postMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.ok(postDtos);
	}
	
	@GetMapping(path = "/drafts")
	public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId){
		User userLoged = userService.getUserById(userId);
		List<Post> posts = postService.getDraftPosts(userLoged);
		List<PostDto> daftarDtos = posts.stream().map(postMapper::toDto).toList();
		return ResponseEntity.ok(daftarDtos);
	}
	
	@PostMapping
	public ResponseEntity<PostDto> createPost(
			@Valid @RequestBody CreatePostRequestDto createPostRequestDto,
			@RequestAttribute UUID userId
	){
		User userLogged = userService.getUserById(userId);
		CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto); // di sini data untuk komunikasi ke service layer
		Post posts = postService.createPost(createPostRequest, userLogged);
		PostDto postDto = postMapper.toDto(posts);
		return new ResponseEntity<>(postDto, HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<PostDto> updatePost(@PathVariable UUID id, @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto){
		UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
		Post posts = postService.updatePost(updatePostRequest, id);
		PostDto postDto = postMapper.toDto(posts);
		return ResponseEntity.ok(postDto);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<PostDto> getPost(@PathVariable UUID id){
		Post posts = postService.getPost(id);
		PostDto postsDto = postMapper.toDto(posts);
		return ResponseEntity.ok(postsDto);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable UUID id){
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}
}