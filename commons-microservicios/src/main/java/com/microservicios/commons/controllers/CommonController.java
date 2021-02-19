package com.microservicios.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.microservicios.commons.services.CommonService;


@RestController
public class CommonController<E, S extends CommonService<E>> {

	@Autowired
	protected S service;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/pagina")
	public ResponseEntity<?> listar(Pageable pageable) {
		
		return ResponseEntity.ok().body(service.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> ver( @PathVariable Long id ) {
		
		Optional<E> o = service.findById(id);
	
		if( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		} 
		
		return ResponseEntity.ok(o.get());
		
	}
	
	@PostMapping
	public ResponseEntity<?> crear( @Validated @RequestBody E enntity, BindingResult result ) {
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		E enntityDb = service.save(enntity);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(enntityDb);
		
	}
	
	
	
	@DeleteMapping( "/{id}" )
	public ResponseEntity<?> eliminar( @PathVariable Long id ) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	protected ResponseEntity<?> validar( BindingResult result ) {
		Map<String, Object> erroresMap = new HashMap<>();
		result.getFieldErrors().forEach( err -> {
			erroresMap.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(erroresMap);
	}
	
}
