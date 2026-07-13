package ar.edu.unq.po2.teststrategy;

import ar.edu.unq.po2.strategy.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EncriptadorNaiveTest {

	@Test
	void usaLaFormaConLaQueSeCreo() {
		EncriptadorNaive enc = new EncriptadorNaive(new LetraANumero());
		assertEquals("4,9,5,7,15", enc.encriptar("diego"));
	}

	@Test
	void desencriptarTambienDelegaEnLaForma() {
		EncriptadorNaive enc = new EncriptadorNaive(new LetraANumero());
		assertEquals("diego", enc.desencriptar("4,9,5,7,15"));
	}

	@Test
	void setFormaCambiaElComportamientoEnTiempoDeEjecucion() {
		EncriptadorNaive enc = new EncriptadorNaive(new OrdenPalabras());
		assertEquals("mundo hola", enc.encriptar("hola mundo"));

		enc.setForma(new LetraANumero()); // cambio de estrategia
		assertEquals("4,9,5,7,15", enc.encriptar("diego"));
	}
}
