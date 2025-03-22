package com.roniantonius.barang.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.roniantonius.barang.domain.entities.Tag;
import com.roniantonius.barang.repositories.TagRepository;
import com.roniantonius.barang.services.TagService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
	private final TagRepository tagRepository;

	@Override
	public List<Tag> getTags() {
		// TODO Auto-generated method stub
		return tagRepository.findAllWithPostCount();
	}

	@Transactional
	@Override
	public List<Tag> createTags(Set<String> tagNames) {
		// caranya ambil kumpulan Tag, kita bakal create atau build tag baru yang nggak ada di kumpulan Tag
		List<Tag> daftarTagList = tagRepository.findByNameIn(tagNames);
		Set<String> daftarTagSet = daftarTagList.stream()
				.map(Tag::getName)
				.collect(Collectors.toSet());
		
		List<Tag> tagBaru = tagNames.stream()
				.filter(nama -> !daftarTagSet.contains(nama))
				.map(nama -> Tag.builder()
						.name(nama)
						.posts(new HashSet<>())
						.build())
				.toList();
		
		List<Tag> simpanList = new ArrayList<>();
		if (!!tagBaru.isEmpty()) {
			simpanList = tagRepository.saveAll(tagBaru);
		}
		
		simpanList.addAll(daftarTagList);
		return null;
	}

	@Override
	public void deleteTags(UUID id) {
		// TODO Auto-generated method stub
		tagRepository.findById(id).ifPresent(tag -> {
			if (!tag.getPosts().isEmpty()) {
				throw new IllegalStateException("Tidak bisa menghapus tag tersebut karena ada Post yang terkait");
			}
			tagRepository.deleteById(id);
		});
	}

	@Override
	public Tag getTagById(UUID id) {
		// TODO Auto-generated method stub
		return tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found id " + id));
	}

	@Override
	public List<Tag> getTagByIds(Set<UUID> ids) {
		// TODO Auto-generated method stub
		List<Tag> tags = tagRepository.findAllById(ids);
		if (tags.size() != ids.size()) {
			throw new EntityNotFoundException("Tidak semua tag yang diinginkan ditemukan");
		}
		return tags;
	}

}
