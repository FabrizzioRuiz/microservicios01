package com.microservicios.app.respuesta.services;

//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.microservicios.app.respuesta.clients.ExamenFeignClient;
import com.microservicios.app.respuesta.models.entity.Respuesta;
import com.microservicios.app.respuesta.models.repository.RespuestaRepository;
//import com.microservicios.common.examenes.models.entity.Examen;
//import com.microservicios.common.examenes.models.entity.Pregunta;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	private RespuestaRepository repository;
	
//	@Autowired
//	private ExamenFeignClient examenClient;
	
	@Override
//	@Transactional
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuesta) {
		
		return repository.saveAll(respuesta);
	}

	@Override
//	@Transactional(readOnly = true) -- POR DFECTO MONGO NO ES TRANSACTIONAL
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
//		Examen examen = examenClient.obtenerExamenPorId(examenId);
//		
//		List<Pregunta> preguntas = examen.getPreguntas();
//		
//		List<Long> preguntaIds = preguntas.stream().map( p -> p.getId()).collect(Collectors.toList());
//		
//		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByAlumnoByPreguntaIds(alumnoId, preguntaIds);
//		
//		//Aqui agregamos el obj. preguntas si coinciden los ids 
//		//Las preguntas las fuimos a buscar mediante el microservicio examen
//		respuestas = respuestas.stream().map( r -> {
//			preguntas.forEach( p -> {
//				if(p.getId() == r.getPreguntaId()) {
//					r.setPregunta(p);
//				}
//			});
//			return r;
//		}).collect(Collectors.toList());
		
		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByAlumnoByExamen(alumnoId, examenId );
		
		
		return respuestas;
	}

	@Override
//	@Transactional(readOnly = true)
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
		//1.Obtenemos la lista de respuestas de Alumnos
//		List<Respuesta> respuestasALumnos = (List<Respuesta>) repository.findByAlumnoId(alumnoId);
//		
//		List<Long> examenIds = Collections.emptyList();
//		
//		if (respuestasALumnos.size() > 0) {
//			List<Long> preguntaIds = respuestasALumnos.stream().map(r-> r.getPreguntaId()).collect(Collectors.toList());
//			examenIds = examenClient.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntaIds);
//		}
		
		List<Respuesta> respuestasAlumnos = (List<Respuesta>) repository.findExamenesIdsConRespuestasByAlumno(alumnoId);
		List<Long> examenIds =  respuestasAlumnos
				.stream()
				.map(r-> r.getPregunta().getExamen().getId()).
				distinct().collect(Collectors.toList());
		
		
		return examenIds;
	}

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
		return repository.findByAlumnoId(alumnoId);
	}

}
