package ar.edu.unq.po2.strategy;

/**
 * ConcreteStrategy (forma por defecto): invierte el orden de las palabras.
 * Ej: "hola mundo cruel" -> "cruel mundo hola".
 * Es su propia inversa: invertir dos veces restaura el texto original,
 * por eso desencriptar() reutiliza encriptar().
 */
public class OrdenPalabras implements FormaDeEncriptacion {

	@Override
	public String encriptar(String texto) {
		String[] palabras = texto.split(" "); //split corta el texto cada vez que encuentra un espacio, y te devuelve un array
		StringBuilder sb = new StringBuilder();
		for (int i = palabras.length - 1; i >= 0; i--) { //arranco en la última posición, hasta i>= 0 y voy restando 1
			sb.append(palabras[i]);
			if (i > 0) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	@Override
	public String desencriptar(String texto) {
		return encriptar(texto);
	}
}
