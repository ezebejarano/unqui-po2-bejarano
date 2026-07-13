package ar.edu.unq.po2.unqshop.catalogo;

/**
 * Identificador de un producto (Stock Keeping Unit).
 * Es un value object: dos SKU con el mismo valor se consideran iguales.
 */
public class SKU {

	private final String valor;

	public SKU(String valor) {
		if (valor == null || valor.isBlank()) {
			throw new IllegalArgumentException("El SKU no puede estar vacio");
		}
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SKU)) {
			return false;
		}
		SKU otro = (SKU) obj;
		return this.valor.equals(otro.valor);
	}

	@Override
	public int hashCode() {
		return valor.hashCode();
	}

	@Override
	public String toString() {
		return valor;
	}
}
