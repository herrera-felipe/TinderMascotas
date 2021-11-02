package com.practica.tinder.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica.tinder.entitys.Mascota;
import com.practica.tinder.entitys.Voto;
import com.practica.tinder.errores.ErrorServicio;
import com.practica.tinder.repositories.MascotaRepositorio;
import com.practica.tinder.repositories.VotoRepositorio;

@Service
public class VotoServicio {

	@Autowired
	private MascotaRepositorio mascotaRepositorio;
	
	@Autowired
	private VotoRepositorio votoRepositorio;

	/*
	 * Este metodo recibe el id del usuario logeado, el idMascota1 que es la mascota
	 * votante; y el idMascota2 que es la mascota que esta recibiendo el voto.
	 */
	public void votar(String idUsuario, String idMascota1, String idMascota2) throws ErrorServicio {

		Voto voto = new Voto(); // Creamos la entidad Voto
		voto.setFecha(new Date()); // Seteamos la fecha del voto

		// ======================================================================================================
		/*
		 * Validamos que los id de mascotas sean distintos para que no se generen auto
		 * votos, esto no deberia ocurrir pero lo contemplamos por las dudas.
		 */
		if (idMascota1.equals(idMascota2)) {
			throw new ErrorServicio("No puede votarse a si mismo.");
		}

		// ======================================================================================================
		// mascota1 la que realiza voto

		Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota1); // Buscamos la mascota que esta votando

		// Validamos el Id de la mascota
		if (respuesta.isPresent()) {
			Mascota mascota1 = respuesta.get(); // obtenemos la mascota a la que corresponde el id
			// Verificamos que el usuario sea el dueño de la mascota1
			if (mascota1.getUsuario().getId().equals(idUsuario)) {
				voto.setMascota1(mascota1); // vinculamos el voto realizado
			} else {
				throw new ErrorServicio("No tiene permisos para realizar la operación solicitada.");
			}
		} else {
			throw new ErrorServicio("No existe una mascota vinculada a ese identificador.");
		}

		// ======================================================================================================
		// mascota2 la que recibe el voto

		Optional<Mascota> respuesta2 = mascotaRepositorio.findById(idMascota2);

		if (respuesta2.isPresent()) {
			Mascota mascota2 = respuesta2.get(); // obtenemos la mascota
			voto.setMascota2(mascota2); // vinculamos el voto recibido
		} else {
			throw new ErrorServicio("No existe una mascota vinculada a ese identificador.");
		}
		
		votoRepositorio.save(voto); // Persistimos el voto en la BD con el repositorio
	}
	
	
	/*
	 * Este metodo lo va generar un usuario, y se va a generar sobre un voto que ya existe
	 * es decir cuando otro usuario a votado a "mi mascota", ingreso a ese voto y genero una respuesta.
	 * Recibe el id del usuario que responde y el id del voto recibido, para validar y dar una repuesta
	 */
	public void responderVoto(String idUsuario, String idVoto) throws ErrorServicio {
		
		// buscamos el voto
		Optional<Voto> respuesta = votoRepositorio.findById(idVoto);
		
		// Validamo si el voto existe
		if (respuesta.isPresent()) {
			Voto voto = respuesta.get();
			voto.setRespuesta(new Date()); // seteamos la fecha de respuesta del voto
			
			// Validamos que el idUsuario corresponda al idUsuario de la mascota2 que recibio el voto anteriormente
			if (voto.getMascota2().getUsuario().getId().equals(idUsuario)) {
				votoRepositorio.save(voto); // persistimos la respuesta
			} else {
				throw new ErrorServicio("No tiene permisos para realizar la operación solicitada.");
			}
		} else {
			throw new ErrorServicio("No existe el voto solicitado.");
		}
		
	}
}
