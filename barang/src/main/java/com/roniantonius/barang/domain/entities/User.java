package com.roniantonius.barang.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id; // JPA Hibernate akan membuat nama atribut secara otomatis berdasarkan nama instance variabel ini
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();
	
	@Column(nullable = false)
	private LocalDateTime createAt;
	
	// disini kita kan menerapkan MapStruct, jadi kita harus bikin method equal() dan hashCode() secara manual, karena bentrok dengan Mapstruct lombok
	@Override
	public int hashCode() {
		return Objects.hash(createAt, email, id, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(createAt, other.createAt) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password);
	}
	
	@PrePersist
	protected void onCreate() {
		this.createAt = LocalDateTime.now();
	}
}