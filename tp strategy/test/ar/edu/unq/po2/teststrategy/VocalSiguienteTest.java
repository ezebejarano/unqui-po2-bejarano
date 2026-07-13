package ar.edu.unq.po2.teststrategy;

import ar.edu.unq.po2.strategy.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VocalSiguienteTest {

	private VocalSiguiente forma;

	@BeforeEach
	void setUp() {
		forma = new VocalSiguiente();
	}

	@Test
	void encriptarCorreCadaVocalALaSiguiente() {
		// a->e, e->i, i->o, o->u, u->a
		assertEquals("eioua", forma.encriptar("aeiou"));
	}

	@Test
	void encriptarDejaIgualLasConsonantes() {
		// casa -> c, a->e, s, a->e
		assertEquals("cese", forma.encriptar("casa"));
	}

	@Test
	void desencriptarHaceElCorrimientoInverso() {
		assertEquals("aeiou", forma.desencriptar("eioua"));
	}

	@Test
	void encriptarYLuegoDesencriptarDevuelveElOriginal() {
		assertEquals("murcielago", forma.desencriptar(forma.encriptar("murcielago")));
	}
}
