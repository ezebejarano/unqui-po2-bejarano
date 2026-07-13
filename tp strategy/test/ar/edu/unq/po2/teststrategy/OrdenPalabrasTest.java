package ar.edu.unq.po2.teststrategy;

import ar.edu.unq.po2.strategy.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdenPalabrasTest {

	private OrdenPalabras forma;

	@BeforeEach
	void setUp() {
		forma = new OrdenPalabras();
	}

	@Test
	void encriptarInvierteElOrdenDeLasPalabras() {
		assertEquals("cruel mundo hola", forma.encriptar("hola mundo cruel"));
	}

	@Test
	void unaSolaPalabraQuedaIgual() {
		assertEquals("hola", forma.encriptar("hola"));
	}

	@Test
	void desencriptarVuelveAlOrdenOriginal() {
		assertEquals("hola mundo cruel", forma.desencriptar("cruel mundo hola"));
	}

	@Test
	void encriptarYLuegoDesencriptarDevuelveElOriginal() {
		// invertir dos veces restaura el texto (operacion involutiva)
		assertEquals("uno dos tres", forma.desencriptar(forma.encriptar("uno dos tres")));
	}
}
