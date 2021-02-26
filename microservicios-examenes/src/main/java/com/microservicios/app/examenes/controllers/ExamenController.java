package com.microservicios.app.examenes.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.examenes.services.ExamenService;
import com.microservicios.common.examenes.models.entity.Examen;
import com.microservicios.common.examenes.models.entity.Pregunta;
import com.microservicios.commons.controllers.CommonController;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService>{

	@GetMapping("/respondidos-por-preguntas")
	public ResponseEntity<?> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntaIds){
		return ResponseEntity.ok().body(service.findExamenesIdsConRespuestasByPreguntaIds(preguntaIds));
	}
	
	
	@PutMapping( "/{id}" )
	public ResponseEntity<?> editar( @Validated @RequestBody Examen examen, BindingResult result , @PathVariable Long id ) {
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Examen> o = service.findById(id);
		
		if( !o.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		
		Examen examenDb = o.get();
		
		examenDb.setNombre(examen.getNombre());
		
		/****/
//		List<Pregunta> eliminadas = new ArrayList<Pregunta>();
//		examenDb.getPreguntas().forEach( pdb -> {
//			if( !examen.getPreguntas().contains(pdb) ) {
//				eliminadas.add(pdb);
//			}
//		});
//		
//		eliminadas.forEach( p -> {
//			examenDb.removePreguntas(p);
//		});
		/****/
		/**OPTIMIZADO**/
		
		List<Pregunta> eliminadas = examenDb.getPreguntas()
		.stream()
		.filter(pdb -> !examen.getPreguntas().contains(pdb))
		.collect(Collectors.toList());
 
		eliminadas.forEach(examenDb::removePregunta);
		
		examenDb.setPreguntas(examen.getPreguntas());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
		
	}
	
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar( @PathVariable String term ) {
		return ResponseEntity.ok(service.findByNombre(term));
	}
	
	
	/**ASIGNATURAS**/
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas() {
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
	
}
