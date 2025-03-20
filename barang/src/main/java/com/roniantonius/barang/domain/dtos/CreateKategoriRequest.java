package com.roniantonius.barang.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateKategoriRequest {
	
	@NotBlank(message = "Kategori tidak boleh kosong.")
	@Size(min = 2, max = 50, message = "Kategori harus memiliki minimal {min} dan maksimal {max} huruf.")
	@Pattern(regexp = "^[\\w\\s-]+$", message = "Kategori harus mengandung huruf, angka , spasi, dan hyphens")
	private String nama;
}
