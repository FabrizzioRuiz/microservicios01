package com.microservicios.app.respuesta.services;

import com.microservicios.app.respuesta.models.entity.Respuesta;

public interface RespuestaService {

	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuesta);
	
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
	
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
	
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId);
}
