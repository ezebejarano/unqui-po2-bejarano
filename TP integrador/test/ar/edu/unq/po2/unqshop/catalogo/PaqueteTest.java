package ar.edu.unq.po2.unqshop.catalogo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaqueteTest {

	private Producto auriculares;
	private Producto funda;
	private Producto cable;
	private Paquete packAudioMovil;

	@BeforeEach
	void setUp() {
		auriculares = new Producto(new SKU("AUR-001"), "Auriculares Bluetooth", "Sony",
				Categoria.ELECTRONICA, 8000.0);
		funda = new Producto(new SKU("FUN-001"), "Funda protectora", "Generica",
				Categoria.ELECTRONICA, 1500.0);
		cable = new Producto(new SKU("CAB-001"), "Cable USB-C", "Generica",
				Categoria.ELECTRONICA, 800.0);

		packAudioMovil = new Paquete("Pack Audio Movil", 0.15);
		packAudioMovil.agregar(auriculares);
		packAudioMovil.agregar(funda);
		packAudioMovil.agregar(cable);
	}

	@Test
	void elPrecioDelPaqueteEsLaSumaDeSusItemsConElDescuentoAplicado() {
		// (8000 + 1500 + 800) * (1 - 0,15) = 10300 * 0,85 = 8755
		assertEquals(8755.0, packAudioMovil.getPrecioBase(), 0.001);
	}

	@Test
	void elPrecioSeCalculaRecursivamenteConPaquetesAnidados() {
		Producto teclado = new Producto(new SKU("TEC-001"), "Teclado", "Logitech",
				Categoria.ELECTRONICA, 5000.0);
		Producto mouse = new Producto(new SKU("MOU-001"), "Mouse", "Logitech",
				Categoria.ELECTRONICA, 2000.0);

		Paquete kitHomeOffice = new Paquete("Kit Home Office", 0.10);
		kitHomeOffice.agregar(packAudioMovil); // un paquete dentro de otro paquete
		kitHomeOffice.agregar(teclado);
		kitHomeOffice.agregar(mouse);

		// (8755 + 5000 + 2000) * (1 - 0,10) = 15755 * 0,90 = 14179,5
		assertEquals(14179.5, kitHomeOffice.getPrecioBase(), 0.001);
	}

	@Test
	void unPaquetePerteneceAUnaCategoriaSiAlgunoDeSusItemsLaTiene() {
		assertTrue(packAudioMovil.perteneceA(Categoria.ELECTRONICA));
		assertFalse(packAudioMovil.perteneceA(Categoria.INDUMENTARIA));
	}

	@Test
	void unPaqueteConTodosSusItemsValidosEsValido() {
		// no debe lanzar excepcion
		packAudioMovil.validar();
	}

	@Test
	void unPaqueteVacioNoEsValido() {
		Paquete vacio = new Paquete("Pack vacio", 0.10);
		assertThrows(ValidacionInvalidaException.class, () -> vacio.validar());
	}

	@Test
	void unPaqueteConUnItemInvalidoNoEsValido() {
		Producto invalido = new Producto(new SKU("X-001"), "", "Generica",
				Categoria.OTROS, 100.0);
		packAudioMovil.agregar(invalido);
		assertThrows(ValidacionInvalidaException.class, () -> packAudioMovil.validar());
	}
}
