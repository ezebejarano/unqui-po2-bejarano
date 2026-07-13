package ar.edu.unq.po2.unqshop.catalogo;

import java.util.HashMap;
import java.util.Map;

/**
 * Producto individual del catalogo (rol Leaf del patron Composite).
 *
 * Ademas de sus atributos fijos (SKU, nombre, marca, categoria, precio), permite
 * agregar atributos dinamicos (por ejemplo Alto, Ancho, Peso) que NO son campos de
 * la clase, mediante un mapa clave-valor (patron Properties). Asi el modelo es
 * escalable: se agregan atributos nuevos sin modificar la clase.
 */
public class Producto implements ItemCatalogo {

	private SKU sku;
	private String nombre;
	private String descripcion;
	private String marca;
	private Categoria categoria;
	private double precio;
	private double descuentoPromocional; // entre 0 y 1
	private Map<String, Object> atributos;

	public Producto(SKU sku, String nombre, String marca, Categoria categoria, double precio) {
		this(sku, nombre, marca, categoria, precio, 0.0);
	}

	public Producto(SKU sku, String nombre, String marca, Categoria categoria, double precio,
			double descuentoPromocional) {
		if (descuentoPromocional < 0 || descuentoPromocional > 1) {
			throw new IllegalArgumentException("El descuento promocional debe estar entre 0 y 1");
		}
		this.sku = sku;
		this.nombre = nombre;
		this.marca = marca;
		this.categoria = categoria;
		this.precio = precio;
		this.descuentoPromocional = descuentoPromocional;
		this.descripcion = "";
		this.atributos = new HashMap<String, Object>();
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public SKU getSku() {
		return sku;
	}

	public String getMarca() {
		return marca;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	/** Precio de lista, sin el descuento promocional. */
	public double getPrecio() {
		return precio;
	}

	public double getDescuentoPromocional() {
		return descuentoPromocional;
	}

	/** Precio con el descuento promocional propio aplicado. */
	@Override
	public double getPrecioBase() {
		return precio * (1 - descuentoPromocional);
	}

	@Override
	public boolean perteneceA(Categoria categoria) {
		return this.categoria == categoria;
	}

	// --- Atributos dinamicos (patron Properties) ---

	/** Agrega o reemplaza un atributo dinamico con un valor asignado. */
	public void agregarAtributo(String clave, Object valor) {
		atributos.put(clave, valor);
	}

	/** Declara un atributo dinamico obligatorio todavia sin valor (validar() fallara hasta asignarlo). */
	public void requerirAtributo(String clave) {
		atributos.put(clave, null);
	}

	public Object getAtributo(String clave) {
		return atributos.get(clave);
	}

	public boolean tieneAtributo(String clave) {
		return atributos.containsKey(clave);
	}

	/**
	 * Comprueba que los atributos obligatorios (nombre y SKU) y los dinamicos tengan
	 * un valor asignado. Solo verifica la asignacion del valor, no su contenido.
	 */
	@Override
	public void validar() {
		if (sku == null) {
			throw new ValidacionInvalidaException("El producto no tiene SKU asignado");
		}
		if (nombre == null || nombre.isBlank()) {
			throw new ValidacionInvalidaException("El producto no tiene nombre asignado");
		}
		for (Map.Entry<String, Object> atributo : atributos.entrySet()) {
			if (atributo.getValue() == null) {
				throw new ValidacionInvalidaException(
						"El atributo dinamico '" + atributo.getKey() + "' no tiene valor asignado");
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Producto)) {
			return false;
		}
		Producto otro = (Producto) obj;
		if (this.sku == null || otro.sku == null) {
			return false;
		}
		return this.sku.equals(otro.sku);
	}

	@Override
	public int hashCode() {
		if (sku == null) {
			return 0;
		}
		return sku.hashCode();
	}
}
