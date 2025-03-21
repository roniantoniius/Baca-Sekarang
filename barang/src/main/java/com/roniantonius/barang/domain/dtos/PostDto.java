package com.roniantonius.barang.domain.dtos;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.roniantonius.barang.domain.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
	private UUID id;
	private String title;
	private String content;
	private PostStatus status;
	private Integer readingTime;
	// ToDo: Author
	private AuthorDto author;
	private KategoriDto kategori;
	private Set<TagDto> tags;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
