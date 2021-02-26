package com.microservicios.app.respuesta.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservicios.common.examenes.models.entity.Examen;

@FeignClient(name = "microservicios-examenes")
public interface ExamenFeignClient {
	
	
	//Colocamos las rutas la cual queremos comunicarnos mediante api rest

	@GetMapping("/{id}")
	public Examen obtenerExamenPorId(@PathVariable Long id);
	
	@GetMapping("/respondidos-por-preguntas")
	public List<Long> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntaIds);
	
}
