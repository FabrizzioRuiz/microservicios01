package com.microservicios.app.usuarios.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.microservicios.app.usuarios.services.AlumnoService;
import com.microservicios.commons.alumnos.models.entity.Alumno;
import com.microservicios.commons.controllers.CommonController;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {	
	
	@GetMapping("/alumnos-por-curso")
	public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
		return ResponseEntity.ok(service.findAllById(ids));
	}
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id) {
		Optional<Alumno> o = service.findById(id);
		
		if( o.isEmpty() || o.get().getFoto() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Resource imageResource = new ByteArrayResource(o.get().getFoto());
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
				.body(imageResource);
		
	}
	
	@PutMapping( "/{id}" )
	public ResponseEntity<?> editar( @Validated @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id ) {
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Alumno> o = service.findById(id);
		
		if( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		
		Alumno alumnoDb = o.get();
		alumnoDb.setNombre(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setEmail(alumno.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
		
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar( @PathVariable String term ) {
		return ResponseEntity.ok(service.findByNombreorApellido(term));
	}
	
	/**FOTO**/

	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Validated Alumno alumno, BindingResult result, 
			@RequestParam MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			alumno.setFoto(file.getBytes());
		}
		return super.crear(alumno, result);
	}
	
	@PutMapping( "/editar-con-foto/{id}" )
	public ResponseEntity<?> editarConFoto( @Validated Alumno alumno, BindingResult result, 
			@PathVariable Long id, @RequestParam MultipartFile file ) throws IOException {
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Alumno> o = service.findById(id);
		
		if( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		
		Alumno alumnoDb = o.get();
		alumnoDb.setNombre(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setEmail(alumno.getEmail());
		
		if (!file.isEmpty()) {
			alumnoDb.setFoto(file.getBytes());
		}
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
		
	}
	
	
	
	
	
	
}