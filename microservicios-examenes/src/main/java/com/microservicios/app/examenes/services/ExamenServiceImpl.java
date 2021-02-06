package com.microservicios.app.examenes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.microservicios.app.examenes.models.repository.ExamenRepository;
import com.microservicios.common.examenes.models.entity.Asignatura;
import com.microservicios.common.examenes.models.entity.Examen;
import com.microservicios.commons.services.CommonServiceImpl;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService{

	@Autowired
	private AsignaturaRepository asignaturaRepositoy;
	
	@Override
	@Transactional(readOnly = true)
	public List<Examen> findByNombre(String term) {
		return repository.findByNombre(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Asignatura> findAllAsignaturas() {
		return asignaturaRepositoy.findAll();
	}

}
