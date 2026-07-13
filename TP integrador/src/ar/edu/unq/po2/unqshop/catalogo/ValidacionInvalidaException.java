package ar.edu.unq.po2.unqshop.catalogo;

/**
 * Excepcion de dominio propia que se lanza cuando un item del catalogo no es valido
 * (por ejemplo, le falta asignar un atributo obligatorio o dinamico).
 */
public class ValidacionInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValidacionInvalidaException(String mensaje) {
		super(mensaje);
	}
}
