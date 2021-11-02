package com.practica.tinder.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica.tinder.entitys.Mascota;
import com.practica.tinder.entitys.Usuario;
import com.practica.tinder.enums.SexoMascota;
import com.practica.tinder.errores.ErrorServicio;
import com.practica.tinder.repositories.MascotaRepositorio;
import com.practica.tinder.repositories.UsuarioRepositorio;

@Service
public class MascotaService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private MascotaRepositorio mascotaRepositorio;

	/*
	 * Recibe el id del usuario logeado que desea agregar una mascota, y los datos
	 * de dicha mascota. Usa el repositorio del usuario para buscar al usuario. Usa
	 * el repositorio de la mascota para Persistir en la base de datos.
	 */
	public void agregarMascota(String idUsuario, String nombre, SexoMascota sexo) throws ErrorServicio {
		// Buscar el usuario por el id
		Usuario usuario = usuarioRepositorio.findById(idUsuario).get();

		validar(nombre, sexo);

		// Se crea la entidad
		Mascota mascota = new Mascota();

		// Seteamos los valores del formulario
		mascota.setNombre(nombre);
		mascota.setSexo(sexo);
		mascota.setAlta(new Date());

		mascotaRepositorio.save(mascota); // guardamos en la bd
	}

	/*
	 * Recibe el id del usuario que esta haciendo la modificacion, el id de la
	 * mascota a modificar y los valores a setear
	 */
	public void modificarMascota(String idUsuario, String idMascota, String nombre, SexoMascota sexo)
			throws ErrorServicio {

		validar(nombre, sexo);

		// Buscamos la mascota en la Bd
		Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);

		// Si existe la mascota
		if (respuesta.isPresent()) {

			Mascota mascota = respuesta.get(); // obtenemos la mascota

			// Verificamos que el usuario que esta modificando sea el dueño de la mascota
			if (mascota.getUsuario().getId().equals(idUsuario)) {
				// realizamos cambios
				mascota.setNombre(nombre);
				mascota.setSexo(sexo);
				mascotaRepositorio.save(mascota); // Actualiza en la BD
			} else {
				throw new ErrorServicio("No tiene permisos suficientes para realizar la operación.");
			}

		} else {
			throw new ErrorServicio("No existe una mascota con el identificador solicitado.");
		}
	}

	/*
	 * Este metodo se va llamar cuando un usuario dueño de una mascota quiera eliminar de la aplicacion
	 * a una de sus mascotas.
	 * Recibe le Id del usuario y el id de la mascota a eliminar
	 */
	public void eliminarMascota(String idUsuario, String idMascota) throws ErrorServicio {

		// Buscamos la mascota en la Bd
		Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);

		// Si existe la mascota
		if (respuesta.isPresent()) {
			Mascota mascota = respuesta.get(); // obtenemos la mascota
			// Verificamos que el usuario que quiere eliminar mascota sea el dueño
			if (mascota.getUsuario().getId().equals(idUsuario)) {
				mascota.setBaja(new Date());
				mascotaRepositorio.save(mascota); 
			}
		} else {
			throw new ErrorServicio("No existe una mascota con el identificador solicitado.");
		}
	}

	public void validar(String nombre, SexoMascota sexo) throws ErrorServicio {
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre no puede estar vacio.");
		}

		if (sexo == null) {
			throw new ErrorServicio("El sexo de la mascota no puede ser nulo.");
		}

	}
}
