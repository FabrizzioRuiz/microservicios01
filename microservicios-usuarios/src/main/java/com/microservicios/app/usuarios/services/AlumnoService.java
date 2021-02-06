package com.microservicios.app.usuarios.services;

import java.util.List;

import com.microservicios.commons.alumnos.models.entity.Alumno;
import com.microservicios.commons.services.CommonService;


public interface AlumnoService extends CommonService<Alumno>{
	
	public List<Alumno> findByNombreorApellido( String term );
	

}
