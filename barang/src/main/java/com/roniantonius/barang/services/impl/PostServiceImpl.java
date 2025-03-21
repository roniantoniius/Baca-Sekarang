package com.roniantonius.barang.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.roniantonius.barang.domain.PostStatus;
import com.roniantonius.barang.domain.entities.Kategori;
import com.roniantonius.barang.domain.entities.Post;
import com.roniantonius.barang.domain.entities.Tag;
import com.roniantonius.barang.repositories.PostRepository;
import com.roniantonius.barang.services.KategoriService;
import com.roniantonius.barang.services.PostService;
import com.roniantonius.barang.services.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
	private final PostRepository postRepository;
	private final KategoriService kategoriService;
	private final TagService tagService;
	
	@Override
	public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
		// TODO Auto-generated method stub
		if (categoryId == null && tagId == null) {
			Kategori kategori = kategoriService.getKategoriById(tagId);
			Tag tag = tagService.getTagById(tagId);
			return postRepository.findAllByStatusAndKategoriAndTagsContaining(PostStatus.PUBLISHED, kategori, tag);
		}
		
		if (categoryId != null) {
			Kategori kategori = kategoriService.getKategoriById(tagId);
			return postRepository.findAllByStatusAndKategori(PostStatus.PUBLISHED, kategori);
		}
		
		if (tagId != null) {
			Tag tag = tagService.getTagById(tagId);
			return postRepository.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED, tag);
		}
		
		return postRepository.findAllByStatus(PostStatus.PUBLISHED);
	}

}
