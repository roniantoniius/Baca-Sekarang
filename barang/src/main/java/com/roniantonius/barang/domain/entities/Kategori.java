package com.roniantonius.barang.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kategori")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Kategori {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, unique = true)
	private String nama;
	
	@OneToMany(mappedBy = "kategori")
	private List<Post> posts = new ArrayList<>();

	@Override
	public int hashCode() {
		return Objects.hash(id, nama);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kategori other = (Kategori) obj;
		return Objects.equals(id, other.id) && Objects.equals(nama, other.nama);
	}
}
