package com.microservicios.app.usuarios.services;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.microservicios.commons.alumnos.models.entity.Alumno;
import com.microservicios.commons.services.CommonService;


public interface AlumnoService extends CommonService<Alumno>{
	
	public List<Alumno> findByNombreorApellido( String term );
	
	public Iterable<Alumno> findAllById(Iterable<Long> ids);
	
	public void eliminarCursoPorId(@PathVariable Long id);
	

}
