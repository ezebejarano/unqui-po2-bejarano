package ar.edu.unq.po2.strategy;

/**
 * Context: usa una FormaDeEncriptacion para encriptar/desencriptar.
 * No sabe COMO se encripta: delega en la forma que tenga asignada,
 * y esa forma se puede cambiar en tiempo de ejecucion con setForma(...).
 */
public class EncriptadorNaive {

	private FormaDeEncriptacion forma;

	public EncriptadorNaive(FormaDeEncriptacion forma) {
		this.forma = forma;
	}

	public void setForma(FormaDeEncriptacion forma) {
		this.forma = forma;
	}

	public String encriptar(String texto) {
		return forma.encriptar(texto);
	}

	public String desencriptar(String texto) {
		return forma.desencriptar(texto);
	}
}
