package ar.edu.unq.po2.unqshop.catalogo;

/**
 * Contrato comun de todo elemento del catalogo (rol Component del patron Composite).
 * Tanto un Producto (hoja) como un Paquete (compuesto) responden a este contrato,
 * por lo que el cliente que navega el catalogo no necesita distinguir entre ellos.
 */
public interface ItemCatalogo {

	String getNombre();

	String getDescripcion();

	/**
	 * Precio efectivo del item.
	 * - Para un Producto: su precio con el descuento promocional propio aplicado.
	 * - Para un Paquete: la suma de los precios base de sus items por (1 - descuento).
	 */
	double getPrecioBase();

	/** Indica si el item pertenece a la categoria dada (lo usa la busqueda del punto 2.6). */
	boolean perteneceA(Categoria categoria);

	/** Lanza ValidacionInvalidaException si falta asignar algun atributo obligatorio. */
	void validar();
}
