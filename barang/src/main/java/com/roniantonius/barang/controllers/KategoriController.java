package com.roniantonius.barang.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.barang.domain.dtos.CreateKategoriRequest;
import com.roniantonius.barang.domain.dtos.KategoriDto;
import com.roniantonius.barang.domain.entities.Kategori;
import com.roniantonius.barang.mappers.KategoriMapper;
import com.roniantonius.barang.services.KategoriService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class KategoriController {

	private final KategoriService kategoriService;
	private final KategoriMapper kategoriMapper;
	@GetMapping
	public ResponseEntity<List<KategoriDto>> listKategori(){
		return ResponseEntity.ok(kategoriService.listKategori()
				.stream()
				.map(kategoriMapper::toDto)
				.toList());
	}
	
	@PostMapping
	public ResponseEntity<KategoriDto> createKategori(@Valid @RequestBody CreateKategoriRequest createKategoriRequest){
		Kategori bikinKategori = kategoriMapper.fromRequestToKategori(createKategoriRequest);
		Kategori simpanKategori = kategoriService.createKategori(bikinKategori);
		return new ResponseEntity<>(kategoriMapper.toDto(simpanKategori), HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteKategori(@PathVariable UUID id){
		kategoriService.deleteKategori(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
