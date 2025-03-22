package com.roniantonius.barang.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roniantonius.barang.domain.PostStatus;
import com.roniantonius.barang.domain.entities.Kategori;
import com.roniantonius.barang.domain.entities.Post;
import com.roniantonius.barang.domain.entities.Tag;
import com.roniantonius.barang.domain.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>{
	List<Post> findAllByStatusAndKategoriAndTagsContaining(PostStatus postStatus, Kategori kategori, Tag tag);
	List<Post> findAllByStatusAndKategori(PostStatus postStatus, Kategori kategori);
	List<Post> findAllByStatusAndTagsContaining(PostStatus postStatus, Tag tag);
	List<Post> findAllByStatus(PostStatus postStatus);
	
	List<Post> findAllByAuthorAndStatus(User author, PostStatus status); 
}
