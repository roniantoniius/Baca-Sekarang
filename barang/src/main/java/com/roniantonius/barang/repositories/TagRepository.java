package com.roniantonius.barang.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roniantonius.barang.domain.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID>{

}
