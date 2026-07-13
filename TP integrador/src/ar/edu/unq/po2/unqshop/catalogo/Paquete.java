package ar.edu.unq.po2.unqshop.catalogo;

import java.util.ArrayList;
import java.util.List;

/**
 * Paquete del catalogo (rol Composite del patron Composite).
 *
 * Agrupa varios items (productos u otros paquetes) y aplica un descuento sobre la
 * suma de sus partes. Como un item puede ser a su vez otro Paquete, el calculo del
 * precio funciona de forma recursiva en toda la jerarquia.
 */
public class Paquete implements ItemCatalogo {

	private String nombre;
	private String descripcion;
	private double descuento; // entre 0 y 1
	private List<ItemCatalogo> items;

	public Paquete(String nombre, double descuento) {
		if (descuento < 0 || descuento > 1) {
			throw new IllegalArgumentException("El descuento del paquete debe estar entre 0 y 1");
		}
		this.nombre = nombre;
		this.descripcion = "";
		this.descuento = descuento;
		this.items = new ArrayList<ItemCatalogo>();
	}

	public void agregar(ItemCatalogo item) {
		items.add(item);
	}

	public List<ItemCatalogo> getItems() {
		return items;
	}

	public double getDescuento() {
		return descuento;
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

	/**
	 * Precio del paquete: suma de los precios base de sus items por (1 - descuento).
	 * Es recursivo porque un item puede ser otro paquete.
	 */
	@Override
	public double getPrecioBase() {
		double suma = 0.0;
		for (ItemCatalogo item : items) {
			suma += item.getPrecioBase();
		}
		return suma * (1 - descuento);
	}

	/** Un paquete pertenece a una categoria si alguno de sus items pertenece a ella. */
	@Override
	public boolean perteneceA(Categoria categoria) {
		for (ItemCatalogo item : items) {
			if (item.perteneceA(categoria)) {
				return true;
			}
		}
		return false;
	}

	/** Un paquete es valido si tiene nombre, contiene items y todos sus items son validos. */
	@Override
	public void validar() {
		if (nombre == null || nombre.isBlank()) {
			throw new ValidacionInvalidaException("El paquete no tiene nombre asignado");
		}
		if (items.isEmpty()) {
			throw new ValidacionInvalidaException("El paquete no contiene items");
		}
		for (ItemCatalogo item : items) {
			item.validar();
		}
	}
}
