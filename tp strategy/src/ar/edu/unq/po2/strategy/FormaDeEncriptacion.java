package ar.edu.unq.po2.strategy;

/**
 * Strategy: contrato común a todas las formas de encriptar/desencriptar.
 * Cada forma concreta debe saber encriptar y hacer su operación inversa.
 */
public interface FormaDeEncriptacion {

	String encriptar(String texto);

	String desencriptar(String texto);
}
