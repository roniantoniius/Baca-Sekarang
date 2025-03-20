package com.roniantonius.barang.domain.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.roniantonius.barang.domain.PostStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PostStatus status;
	
	@Column(nullable = false)
	private Integer readingTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kategori_id", nullable = false)
	private Kategori kategori;
	
	@ManyToMany
	@JoinTable(name = "post_tags", joinColumns = @JoinColumn(name="post_id"), inverseJoinColumns = @JoinColumn(name="tag_id"))
	private Set<Tag> tags = new HashSet<>();
	
	@Column(nullable = false)
	private LocalDateTime createAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Override
	public int hashCode() {
		return Objects.hash(content, createAt, id, name, readingTime, status, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(content, other.content) && Objects.equals(createAt, other.createAt)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(readingTime, other.readingTime) && status == other.status
				&& Objects.equals(updatedAt, other.updatedAt);
	}
	
	@PrePersist
	public void onCreated() {
		LocalDateTime sekarang = LocalDateTime.now();
		this.createAt = sekarang;
		this.updatedAt = sekarang;
	}
	
	@PreUpdate
	public void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
