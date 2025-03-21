package com.roniantonius.barang.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.roniantonius.barang.domain.entities.Kategori;
import com.roniantonius.barang.repositories.KategoriRepository;
import com.roniantonius.barang.services.KategoriService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class KategoriServiceImpl implements KategoriService {
	private final KategoriRepository kategoriRepository;
	@Override
	public List<Kategori> listKategori() {
		// TODO Auto-generated method stub
		return kategoriRepository.findAllWithJumlahPost();
	}
	@Override
	public Kategori createKategori(Kategori kategori) {
		// TODO Auto-generated method stub
		if (kategoriRepository.existsByNamaIgnoreCase(kategori.getNama())) {
			throw new IllegalArgumentException("Kategori sudah ada: " + kategori.getNama());
		}
		return kategoriRepository.save(kategori);
	}
	@Override
	public void deleteKategori(UUID id) {
		// TODO Auto-generated method stub
		Optional<Kategori> kategoriOptional = kategoriRepository.findById(id);
		if (kategoriOptional.isPresent()) {
			if(!kategoriOptional.get().getPosts().isEmpty()) {
				throw new IllegalStateException("Kategori tersebut masih ada Post yang berasosiasi.");
			}
			kategoriRepository.deleteById(id);
		}
	}
	@Override
	public Kategori getKategoriById(UUID id) {
		// TODO Auto-generated method stub
		return kategoriRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Kategori tidak ditemukan"));
	}

}
