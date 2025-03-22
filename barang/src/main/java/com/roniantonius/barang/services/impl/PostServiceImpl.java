package com.roniantonius.barang.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.roniantonius.barang.domain.CreatePostRequest;
import com.roniantonius.barang.domain.PostStatus;
import com.roniantonius.barang.domain.UpdatePostRequest;
import com.roniantonius.barang.domain.entities.Kategori;
import com.roniantonius.barang.domain.entities.Post;
import com.roniantonius.barang.domain.entities.Tag;
import com.roniantonius.barang.domain.entities.User;
import com.roniantonius.barang.repositories.PostRepository;
import com.roniantonius.barang.services.KategoriService;
import com.roniantonius.barang.services.PostService;
import com.roniantonius.barang.services.TagService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
	private final PostRepository postRepository;
	private final KategoriService kategoriService;
	private final TagService tagService;
	
	private static final int WORD_PER_MINUTE = 200;

	@Override
	public Post getPost(UUID id) {
		// TODO Auto-generated method stub
		return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post tidak ditemukan dengan id " + id));
	}
	
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

	@Override
	public List<Post> getDraftPosts(User user) {
		// TODO Auto-generated method stub
		return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
	}

	@Transactional
	@Override
	public Post createPost(CreatePostRequest createPostRequest, User user) {
	    Post posts = new Post();
	    posts.setName(createPostRequest.getName());
	    posts.setContent(createPostRequest.getContent());
	    posts.setStatus(createPostRequest.getStatus());
	    posts.setAuthor(user);
	    posts.setReadingTime(calculateReadingTime(createPostRequest.getContent()));
	    Kategori kategori = kategoriService.getKategoriById(createPostRequest.getKategoriId());
	    posts.setKategori(kategori);
	    Set<UUID> idTagSet = createPostRequest.getTagIds();
	    List<Tag> daftarTags = tagService.getTagByIds(idTagSet);
	    posts.setTags(new HashSet<>(daftarTags));

	    return postRepository.save(posts);
	}
	
	@Transactional
	@Override
	public Post updatePost(UpdatePostRequest updatePostRequest, UUID id) {
		// TODO Auto-generated method stub
		Post posts = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post is not found with id " + id));
		posts.setName(updatePostRequest.getName());
		String postContent = updatePostRequest.getContent();
		posts.setContent(postContent);
		posts.setStatus(updatePostRequest.getStatus());
		posts.setReadingTime(calculateReadingTime(postContent));
		
		UUID getKategoriIdRequest = updatePostRequest.getKategoriId();
		if (!posts.getKategori().getId().equals(getKategoriIdRequest)) {
			Kategori kategori = kategoriService.getKategoriById(getKategoriIdRequest);
			posts.setKategori(kategori);
		}
		
		Set<UUID> tagPostLama = posts.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
		Set<UUID> tagPostUpdate = updatePostRequest.getTagIds();
		if (!tagPostLama.equals(tagPostUpdate)) {
			List<Tag> daftarTags = tagService.getTagByIds(tagPostUpdate);
			posts.setTags(new HashSet<>(daftarTags));
		}
		
		return postRepository.save(posts);
	}
	
	private Integer calculateReadingTime(String content) {
		if (content == null || content.isEmpty()) {
			return 0;
		}
		int panjangKata = content.trim().split("\\s+").length;
		return (int) Math.ceil((double) panjangKata / WORD_PER_MINUTE);
	}

	@Override
	public void deletePost(UUID id) {
		// TODO Auto-generated method stub
		Post posts = getPost(id);
		postRepository.delete(posts);
	}



}
