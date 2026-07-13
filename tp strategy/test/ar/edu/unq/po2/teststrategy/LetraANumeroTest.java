package ar.edu.unq.po2.teststrategy;

import ar.edu.unq.po2.strategy.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LetraANumeroTest {

	private LetraANumero forma;

	@BeforeEach
	void setUp() {
		// se ejecuta ANTES de cada test: arranca con una instancia "limpia"
		forma = new LetraANumero();
	}

	@Test
	void encriptarUnaPalabra() {
		// el ejemplo de la consigna: "Diego" -> "4,9,5,7,15"
		assertEquals("4,9,5,7,15", forma.encriptar("diego"));
	}

	@Test
	void encriptarNoDistingueMayusculas() {
		assertEquals("4,9,5,7,15", forma.encriptar("Diego"));
	}

	@Test
	void encriptarUsaElCeroParaElEspacio() {
		// "a b" -> a=1, espacio=0, b=2
		assertEquals("1,0,2", forma.encriptar("a b"));
	}

	@Test
	void desencriptarUnaPalabra() {
		assertEquals("diego", forma.desencriptar("4,9,5,7,15"));
	}

	@Test
	void desencriptarInterpretaElCeroComoEspacio() {
		assertEquals("a b", forma.desencriptar("1,0,2"));
	}

	@Test
	void encriptarYLuegoDesencriptarDevuelveElOriginal() {
		assertEquals("hola mundo", forma.desencriptar(forma.encriptar("hola mundo")));
	}
}
