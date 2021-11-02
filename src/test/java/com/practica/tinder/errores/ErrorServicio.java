package com.practica.tinder.errores;

/*
 * Esta clase se crea para diferencias los errores de nuestra logica del negocio
 * de los errores que ocurren en el sistema
 */
public class ErrorServicio extends Exception {
	
	public ErrorServicio(String msn) {
		super(msn);
	}

}
