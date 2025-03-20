package com.roniantonius.barang.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.roniantonius.barang.domain.entities.Kategori;

@Repository
public interface KategoriRepository extends JpaRepository<Kategori, UUID>{
	@Query("SELECT k from Kategori k LEFT JOIN FETCH k.posts") // mencari semua post yang terdaftar dalam suatu kategori
	List<Kategori> findAllWithJumlahPost();
	
	boolean existsByNamaIgnoreCase(String nama);
}
