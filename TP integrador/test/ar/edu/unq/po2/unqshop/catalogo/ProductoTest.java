package ar.edu.unq.po2.unqshop.catalogo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductoTest {

	private Producto auriculares;

	@BeforeEach
	void setUp() {
		auriculares = new Producto(new SKU("AUR-001"), "Auriculares Bluetooth", "Sony",
				Categoria.ELECTRONICA, 8000.0);
	}

	@Test
	void elPrecioBaseSinPromoEsElPrecioDeLista() {
		assertEquals(8000.0, auriculares.getPrecioBase(), 0.001);
	}

	@Test
	void elPrecioBaseConPromoAplicaElDescuento() {
		Producto enOferta = new Producto(new SKU("TV-001"), "Smart TV", "LG",
				Categoria.ELECTRONICA, 10000.0, 0.10);
		assertEquals(9000.0, enOferta.getPrecioBase(), 0.001);
	}

	@Test
	void unProductoPerteneceASuCategoria() {
		assertTrue(auriculares.perteneceA(Categoria.ELECTRONICA));
		assertFalse(auriculares.perteneceA(Categoria.INDUMENTARIA));
	}

	@Test
	void seLePuedenAgregarYLeerAtributosDinamicos() {
		auriculares.agregarAtributo("Peso", 0.25);
		auriculares.agregarAtributo("Color", "Negro");

		assertTrue(auriculares.tieneAtributo("Peso"));
		assertEquals(0.25, auriculares.getAtributo("Peso"));
		assertEquals("Negro", auriculares.getAtributo("Color"));
	}

	@Test
	void unProductoConTodosSusDatosAsignadosEsValido() {
		auriculares.agregarAtributo("Peso", 0.25);
		// no debe lanzar excepcion
		auriculares.validar();
	}

	@Test
	void unProductoSinNombreNoEsValido() {
		Producto sinNombre = new Producto(new SKU("X-001"), "", "Generica",
				Categoria.OTROS, 100.0);
		assertThrows(ValidacionInvalidaException.class, () -> sinNombre.validar());
	}

	@Test
	void unAtributoDinamicoSinValorAsignadoHaceInvalidoAlProducto() {
		auriculares.requerirAtributo("Peso"); // declarado pero sin valor
		assertThrows(ValidacionInvalidaException.class, () -> auriculares.validar());
	}

	@Test
	void unAtributoDinamicoRequeridoSeVuelveValidoAlAsignarleValor() {
		auriculares.requerirAtributo("Peso");
		auriculares.agregarAtributo("Peso", 0.25);
		// ahora ya tiene valor: no debe lanzar excepcion
		auriculares.validar();
	}

	@Test
	void dosProductosConElMismoSkuSonIguales() {
		Producto otro = new Producto(new SKU("AUR-001"), "Otro nombre", "Otra",
				Categoria.HOGAR, 1.0);
		assertEquals(auriculares, otro);
		assertEquals(auriculares.hashCode(), otro.hashCode());
	}
}
