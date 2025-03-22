package com.roniantonius.barang.domain;
// class for dealing with service layer, since the CreatePostRequestDto already dealing with Presentation layer
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePostRequest {
	private String name;
	
	private String content;
	
	private UUID kategoriId;
	
	@Builder.Default
	private Set<UUID> tagIds = new HashSet<>();
	
	private PostStatus status;
}
