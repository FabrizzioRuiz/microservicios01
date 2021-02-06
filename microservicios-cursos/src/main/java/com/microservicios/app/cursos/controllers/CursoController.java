package com.microservicios.app.cursos.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.cursos.models.entity.Curso;
import com.microservicios.app.cursos.services.CursoService;
import com.microservicios.common.examenes.models.entity.Examen;
import com.microservicios.commons.alumnos.models.entity.Alumno;
import com.microservicios.commons.controllers.CommonController;


@RestController
public class CursoController extends CommonController<Curso, CursoService>{

	@PutMapping( "/{id}" )
	public ResponseEntity<?> editar( @Validated @RequestBody Curso curso, BindingResult result, @PathVariable Long id ) {
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Curso> o = service.findById(id);
		
		if( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDb = o.get();
		cursoDb.setNombre(curso.getNombre());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
		
	}
	
	
	
	@PutMapping( "/{id}/asignar-alumnos" )
	public ResponseEntity<?> asignarAlumnos( @RequestBody List<Alumno> alumnos, @PathVariable Long id ) {
		Optional<Curso> o = service.findById(id);
		
		if( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();
		
		alumnos.forEach( a -> {
			cursoDb.addAlumno(a);
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
	
	@PutMapping( "/{id}/eliminar-alumno" )
	public ResponseEntity<?> eliminarAlumno( @RequestBody Alumno alumno, @PathVariable Long id ) {
		Optional<Curso> o = service.findById(id);
		
		if( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();
		
		cursoDb.removeAlumno(alumno);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId( @PathVariable Long id ) {
		Curso c = service.findCursoByAlumnoId(id);
		
		if (c != null) {
			List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);
			
			List<Examen> examenes = c.getExamenes().stream().map( examen -> {
				if (examenesIds.contains(examen.getId())) {
					examen.setRespondido(true);
				}
				return examen;
			}).collect(Collectors.toList());
			
			c.setExamenes(examenes);
		}
		
		return ResponseEntity.ok(c);
	}
	
	
	@PutMapping( "/{id}/asignar-examenes" )
	public ResponseEntity<?> asignarExamenes( @RequestBody List<Examen> examenes, @PathVariable Long id ) {
		Optional<Curso> o = service.findById(id);
		
		if( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();
		
		examenes.forEach( a -> {
			cursoDb.addExamen(a);
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
	
	@PutMapping( "/{id}/eliminar-examen" )
	public ResponseEntity<?> eliminarExamen( @RequestBody Examen examen, @PathVariable Long id ) {
		Optional<Curso> o = service.findById(id);
		
		if( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();
		
		cursoDb.removeExamen(examen);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
	
}
