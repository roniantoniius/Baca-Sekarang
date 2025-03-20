package com.roniantonius.barang.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.barang.domain.dtos.ApiErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {
	
	@ExceptionHandler(Exception.class) // method ini akan dieksekusi jika error Exception terjadi
	public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
		log.error("Terdapat pengecualian", ex);
		ApiErrorResponse errorResponse = ApiErrorResponse.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("Error yang tidak diekspektasi terjadi")
				.build();
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex){
		ApiErrorResponse errorResponse = ApiErrorResponse.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException ex){
		ApiErrorResponse errorResponse = ApiErrorResponse.builder()
				.status(HttpStatus.CONFLICT.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
		ApiErrorResponse errorResponse = ApiErrorResponse.builder()
				.status(HttpStatus.UNAUTHORIZED.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
}
