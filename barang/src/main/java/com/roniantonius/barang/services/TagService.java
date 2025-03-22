package com.roniantonius.barang.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.roniantonius.barang.domain.entities.Tag;

public interface TagService {
	List<Tag> getTags();
	List<Tag> createTags(Set<String> tagNames);
	void deleteTags(UUID id);
	Tag getTagById(UUID id);
	List<Tag> getTagByIds(Set<UUID> ids);
}
