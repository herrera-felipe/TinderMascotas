package com.practica.tinder.entitys;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Voto {

	// GenratedValue y GenericGenerator son anotacion para que se cree en auto el id y sea unico, no se repita
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha; // fecha que realiza el voto
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date respuesta; // fecha cuando optiene respuesta

	// Hacemos la relacion entre entidades
	@ManyToOne
	private Mascota mascota1; // Mascota que vota
	
	@ManyToOne
	private Mascota mascota2; // mascota que recibe voto
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Date respuesta) {
		this.respuesta = respuesta;
	}

	public Mascota getMascota1() {
		return mascota1;
	}

	public void setMascota1(Mascota mascota1) {
		this.mascota1 = mascota1;
	}

	public Mascota getMascota2() {
		return mascota2;
	}

	public void setMascota2(Mascota mascota2) {
		this.mascota2 = mascota2;
	}
	
	
}
