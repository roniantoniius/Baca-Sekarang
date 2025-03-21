package com.roniantonius.barang.domain.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// this Dto purpose is for not exposing directly user daata into Posts data
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {
	private UUID id;
	private String name;
}
