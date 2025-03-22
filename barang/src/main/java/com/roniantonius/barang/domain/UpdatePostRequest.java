package com.roniantonius.barang.domain;

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
public class UpdatePostRequest {
	private UUID id;
	
	private String name;
	
	private String content;
	
	private UUID kategoriId;
	
	private Set<UUID> tagIds = new HashSet<>();
	
	private PostStatus status;
}
