package com.microservicios.app.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.app.cursos.models.entity.Curso;
import com.microservicios.app.cursos.models.entity.CursoAlumno;
import com.microservicios.app.cursos.services.CursoService;
import com.microservicios.common.examenes.models.entity.Examen;
import com.microservicios.commons.alumnos.models.entity.Alumno;
import com.microservicios.commons.controllers.CommonController;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	@Value("${config.balanceador.test}")
	private String balanceadorTest;

	@DeleteMapping("/eliminar-alumno/{id}")
	public ResponseEntity<?> eliminarCursoPorId(@PathVariable Long id) {
		service.eliminarCursoALumnoPorId(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	@Override
	public ResponseEntity<?> listar() {
		List<Curso> cursos = ((List<Curso>) service.findAll()).stream().map(c -> {

			c.getCursoAlumnos().forEach(ca -> {
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				c.addAlumno(alumno);
			});
			return c;
		}).collect(Collectors.toList());

		return ResponseEntity.ok().body(cursos);
	}

	@GetMapping("/pagina")
	@Override
	public ResponseEntity<?> listar(Pageable pageable) {

		Page<Curso> cursos = service.findAll(pageable).map(c -> {

			c.getCursoAlumnos().forEach(ca -> {
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				c.addAlumno(alumno);
			});
			return c;
		});

		return ResponseEntity.ok().body(cursos);
	}

	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("balanceador", balanceadorTest);
		response.put("cursos", service.findAll());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<?> ver(@PathVariable Long id) {

		Optional<Curso> o = service.findById(id);

		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Curso curso = o.get();

		if (curso.getCursoAlumnos().isEmpty() == false) {

			List<Long> ids = curso.getCursoAlumnos().stream().map(ca -> ca.getAlumnoId()).collect(Collectors.toList());

			List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);

			curso.setAlumnos(alumnos);

		}

		return ResponseEntity.ok(curso);

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Validated @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {

		if (result.hasErrors()) {
			return this.validar(result);
		}

		Optional<Curso> o = service.findById(id);

		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Curso cursoDb = o.get();
		cursoDb.setNombre(curso.getNombre());

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));

	}

	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
		Optional<Curso> o = service.findById(id);

		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();

		alumnos.forEach(a -> {
			CursoAlumno cursoAlumno = new CursoAlumno();

			cursoAlumno.setAlumnoId(a.getId());
			cursoAlumno.setCurso(cursoDb);
//			cursoDb.addAlumno(a);
			cursoDb.addCursoAlumno(cursoAlumno);
		});

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}

	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
		Optional<Curso> o = service.findById(id);

		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();
		CursoAlumno cursoAlumno = new CursoAlumno();

		cursoAlumno.setAlumnoId(alumno.getId());

		cursoDb.removeCursoAlumno(cursoAlumno);

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}

	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id) {
		Curso c = service.findCursoByAlumnoId(id);

		if (c != null) {
			List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);

			if (examenesIds != null && examenesIds.size() > 0) {

				List<Examen> examenes = c.getExamenes().stream().map(examen -> {
					if (examenesIds.contains(examen.getId())) {
						examen.setRespondido(true);
					}
					return examen;
				}).collect(Collectors.toList());

				c.setExamenes(examenes);
			}
		}

		return ResponseEntity.ok(c);
	}

	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
		Optional<Curso> o = service.findById(id);

		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();

		examenes.forEach(a -> {
			cursoDb.addExamen(a);
		});

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}

	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
		Optional<Curso> o = service.findById(id);

		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();

		cursoDb.removeExamen(examen);

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}

}
