package com.roniantonius.barang.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.roniantonius.barang.domain.CreatePostRequest;
import com.roniantonius.barang.domain.UpdatePostRequest;
import com.roniantonius.barang.domain.dtos.CreatePostRequestDto;
import com.roniantonius.barang.domain.dtos.PostDto;
import com.roniantonius.barang.domain.dtos.UpdatePostRequestDto;
import com.roniantonius.barang.domain.entities.Post;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
	
	@Mapping(target = "author", source = "author")
	@Mapping(target = "kategori", source = "kategori")
	@Mapping(target = "tags", source = "tags")
	@Mapping(target = "status", source = "status")
	PostDto toDto(Post post);
	
	CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);
	
	UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto);
}
