package com.microservicios.app.respuesta.models.entity;

import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.microservicios.common.examenes.models.entity.Pregunta;
import com.microservicios.commons.alumnos.models.entity.Alumno;

@Document(collection = "respuestas")
public class Respuesta {

	@Id
	private String id;
	
	private String texto;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	//Transient para que no este mapeado a la tabla
	//@Transient - javax-persistence
	
	//@Transient
	private Alumno alumno;
	
	/**
	 * Ahora no hace falta tener una tabla intermedia porque la relacion es de
	 * una respuesta un alumno. Un alumno puede tener muchas respuestas pero respuestas 
	 * solo un alumno, por eso es solo un atributo
	 */
	//@Column(name = "alumno_id")
	private Long alumnoId;
	
	//@OneToOne(fetch = FetchType.LAZY)
	//@Transient
	private Pregunta pregunta;
	
	private Long preguntaId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public Long getAlumnoId() {
		return alumnoId;
	}

	public void setAlumnoId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}

	public Long getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(Long preguntaId) {
		this.preguntaId = preguntaId;
	}
	
}
