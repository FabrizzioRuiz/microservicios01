package com.microservicios.commons.alumnos.models.entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="alumnos")

public class Alumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min = 4, max = 15)
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email
	private String email;
	
	@Lob
	@JsonIgnore
	private byte[] foto;
	
	@Column(name = "create_at")
	//Para incluir una fecha completa
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public Integer getFotoHashCode() {
		return (this.foto != null)?this.foto.hashCode():null;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	@Override
	public boolean equals(Object obj) {
		
		if( this == obj ) {
			return true;
		}
		
		if( !(obj instanceof Alumno) ) {
			return false;
		}
		
		Alumno a = (Alumno) obj;
		
		return this.id != null && this.id.equals(a.getId());
	}
	
	
	
}
