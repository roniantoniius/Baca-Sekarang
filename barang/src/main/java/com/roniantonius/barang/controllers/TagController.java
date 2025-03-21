package com.roniantonius.barang.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.barang.domain.dtos.CreateTagRequest;
import com.roniantonius.barang.domain.dtos.TagDto;
import com.roniantonius.barang.domain.entities.Tag;
import com.roniantonius.barang.mappers.TagMapper;
import com.roniantonius.barang.services.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
	
	private final TagService tagService;
	private final TagMapper tagMapper;
	
	@GetMapping
	public ResponseEntity<List<TagDto>> getAllTags(){
		List<Tag> tags = tagService.getTags();
		List<TagDto> tagDtos = tags.stream().map(tagMapper::toTagResponse).toList();
		return ResponseEntity.ok(tagDtos);
	}
	
	@PostMapping
	public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagRequest createTagRequest){
		List<Tag> daftarList = tagService.createTags(createTagRequest.getNames());
		List<TagDto> dafTagResponses = daftarList.stream().map(tagMapper::toTagResponse).toList();
		return new ResponseEntity<>(dafTagResponses, HttpStatus.CREATED); 
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteTags(@PathVariable UUID id) {
		tagService.deleteTags(id);
		return ResponseEntity.noContent().build();
	}
}
