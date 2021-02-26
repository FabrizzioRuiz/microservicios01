
package com.microservicios.app.examenes.services;

import java.util.List;

import com.microservicios.common.examenes.models.entity.Asignatura;
import com.microservicios.common.examenes.models.entity.Examen;
import com.microservicios.commons.services.CommonService;

public interface ExamenService extends CommonService<Examen> {

	public List<Examen> findByNombre( String term );
	
	public Iterable<Asignatura> findAllAsignaturas();
	
	public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds);
}
