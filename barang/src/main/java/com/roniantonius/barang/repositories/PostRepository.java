package com.roniantonius.barang.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roniantonius.barang.domain.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>{

}
