package com.roniantonius.barang.domain.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KategoriDto {
	private UUID id;
	private String nama;
	private Long jumlahPost;
}
