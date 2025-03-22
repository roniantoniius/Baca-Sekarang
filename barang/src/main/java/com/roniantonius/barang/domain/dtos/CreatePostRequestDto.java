package com.roniantonius.barang.domain.dtos;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.roniantonius.barang.domain.PostStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePostRequestDto {
	@NotBlank(message = "Artikel tidak boleh kosong.")
	@Size(min = 3, max = 200, message = "Artikel harus memiliki minimal {min} dan maksimal {max} huruf.")
	private String name;
	
	@NotBlank(message = "Konten dari artikel tidak boleh kosong")
	@Size(min = 10, max = 50000, message = "Artikel harus memilki isi konten minimal {min} dan maksimal {max} huruf.")
	private String content;
	
	@NotNull(message = "Kategori konten dari aplikasi harus tidak kosong")
	private UUID kategoriId;
	
	@Builder.Default
	@Size(max = 10, message = "Maksimum {max} jumlah tag diperbolehkan")
	private Set<UUID> tagIds = new HashSet<>();
	
	@NotNull(message = "Status dari artikel antara post / draft")
	private PostStatus status;
}