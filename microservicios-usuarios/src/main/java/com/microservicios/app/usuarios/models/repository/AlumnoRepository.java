package com.microservicios.app.usuarios.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.microservicios.commons.alumnos.models.entity.Alumno;

public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {
	
	//Postgres es sencible a las mayusculas
	//@Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1%")
	
	@Query("select a from Alumno a where upper(a.nombre) like  upper(concat('%', ?1, '%')) or upper(a.apellido) like upper(concat('%', ?1, '%'))")
	public List<Alumno> findByNombreorApellido( String term );

}
