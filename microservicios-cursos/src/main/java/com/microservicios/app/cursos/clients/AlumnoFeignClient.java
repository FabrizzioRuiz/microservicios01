package com.microservicios.app.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservicios.commons.alumnos.models.entity.Alumno;

@FeignClient(name = "microservicios-usuarios")
public interface AlumnoFeignClient {
	
	//Que endpoint vamos a consumir de usuarios
	
	@GetMapping("/alumnos-por-curso")
	public Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);
}
