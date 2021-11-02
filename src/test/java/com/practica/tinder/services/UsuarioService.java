package com.practica.tinder.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica.tinder.entitys.Usuario;
import com.practica.tinder.errores.ErrorServicio;
import com.practica.tinder.repositories.UsuarioRepositorio;

@Service
public class UsuarioService {

	// Creamos como atributo de clase el repositorio
	@Autowired // la variable la inicializa el servidor de aplicaciones
	private UsuarioRepositorio usuarioRepositorio;

	public void registrarUsuario(String nombre, String apellido, String mail, String clave) throws ErrorServicio {

		validarDatos(nombre, apellido, mail, clave);

		// Se crea la entidad
		Usuario usuario = new Usuario();

		// Seteamos los valores del formulario
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setMail(mail);
		usuario.setClave(clave);
		usuario.setAlta(new Date());

		// llamamos el metodo save del repositorio para almacenar en la bd
		usuarioRepositorio.save(usuario);
	}

	public void modificarUsuario(String id, String nombre, String apellido, String mail, String clave)
			throws ErrorServicio {

		validarDatos(nombre, apellido, mail, clave);

		// Buscamos el usuario en la bd por medio del repositorio
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

		// Validamos que exista el usuario
		if (respuesta.isPresent()) {
			// Obtenemos el usuario
			Usuario usuario = respuesta.get();
			// Una vez encontrado seteamos los nuevos valores
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setMail(mail);
			usuario.setClave(clave);

			// Actualizamos en la bd con el metodo save del repositorio
			usuarioRepositorio.save(usuario);
		} else {
			throw new ErrorServicio("No se encontró el usuario solicitado");
		}
	}

	public void deshabilitarUsuario(String id) throws ErrorServicio {
		// Buscamos el usuario en la bd por medio del repositorio
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

		// Validamos que exista el usuario
		if (respuesta.isPresent()) {
			// Obtenemos el usuario
			Usuario usuario = respuesta.get();
			// Una vez encontrado seteamos la fecha de baja
			usuario.setBaja(new Date());

			// Actualizamos en la bd con el metodo save del repositorio
			usuarioRepositorio.save(usuario);
		} else {
			throw new ErrorServicio("No se encontró el usuario solicitado");
		}
	}

	public void habilitarUsuario(String id) throws ErrorServicio {
		// Buscamos el usuario en la bd por medio del repositorio
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

		// Validamos que exista el usuario
		if (respuesta.isPresent()) {
			// Obtenemos el usuario
			Usuario usuario = respuesta.get();
			// Una vez encontrado borramos la fecha de baja
			usuario.setBaja(null);

			// Actualizamos en la bd con el metodo save del repositorio
			usuarioRepositorio.save(usuario);
		} else {
			throw new ErrorServicio("No se encontró el usuario solicitado");
		}
	}

	public void validarDatos(String nombre, String apellido, String mail, String clave) throws ErrorServicio {

		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El campo usuario no puede estar vacio.");
		}

		if (apellido == null || apellido.isEmpty()) {
			throw new ErrorServicio("El campo apellido no puede estar vacio.");
		}

		if (mail == null || mail.isEmpty()) {
			throw new ErrorServicio("El campo mail no puede estar vacio.");
		}

		if (clave == null || clave.isEmpty() || clave.length() < 6) {
			throw new ErrorServicio("La clave del usuario no puede estar vacia, y debe tener mas de 6 digitos");
		}
	}

}
