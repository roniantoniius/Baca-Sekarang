package com.roniantonius.barang.domain.dtos;

import java.util.Set;

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
public class CreateTagRequest {
	@NotBlank(message = "Tag tidak boleh kosong, minimal satu kata.")
	@Size(max = 50, message = "Maksimal {max} diperbolehkan.")
	private Set<
		@Size(min = 2, max = 30, message = "Tag harus memiliki minimal {min} dan maksimal {max} huruf.")
		@Pattern(regexp = "^[\\w\\s-]+$", message = "Tag harus mengandung huruf, angka , spasi, dan hyphens")
		String
	> names;
}
