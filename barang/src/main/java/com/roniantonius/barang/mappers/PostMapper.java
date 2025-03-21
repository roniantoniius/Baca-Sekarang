package com.roniantonius.barang.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.roniantonius.barang.domain.dtos.PostDto;
import com.roniantonius.barang.domain.entities.Post;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
	
	@Mapping(target = "author", source = "author")
	@Mapping(target = "kategori", source = "kategori")
	@Mapping(target = "tags", source = "tags")
	PostDto toDto(Post post);
}
