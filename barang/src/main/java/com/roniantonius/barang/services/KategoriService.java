package com.roniantonius.barang.services;

import java.util.List;
import java.util.UUID;

import com.roniantonius.barang.domain.entities.Kategori;

public interface KategoriService {
	List<Kategori> listKategori();
	Kategori createKategori(Kategori kategori);
	void deleteKategori(UUID id);
}
