package com.roniantonius.barang.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.roniantonius.barang.domain.PostStatus;
import com.roniantonius.barang.domain.dtos.CreateKategoriRequest;
import com.roniantonius.barang.domain.dtos.KategoriDto;
import com.roniantonius.barang.domain.entities.Kategori;
import com.roniantonius.barang.domain.entities.Post;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KategoriMapper {
	
	@Mapping(target = "jumlahPost", source = "posts", qualifiedByName = "calculateJumlahPost")
	KategoriDto toDto(Kategori kategori);
	
	Kategori fromRequestToKategori(CreateKategoriRequest createKategoriRequest);
	
	@Named("calculateJumlahPost")
	default Long calculateJumlahPost(List<Post> posts) {
		if (posts == null) {
			return (long) 0;
		}
		
		return posts.stream()
				.filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
				.count();
	}
}
