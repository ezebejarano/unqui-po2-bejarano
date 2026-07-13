package ar.edu.unq.po2.strategy;

/**
 * ConcreteStrategy: cada letra se escribe como su numero de orden
 * (a=1, b=2, ... z=26), el espacio es 0, separados por comas.
 * Ej: "Diego" -> "4,9,5,7,15".
 * No distingue mayusculas/minusculas (al desencriptar devuelve minusculas).
 * Se asume que el texto solo tiene letras (sin acentos) y espacios.
 */
public class LetraANumero implements FormaDeEncriptacion {

	@Override
	public String encriptar(String texto) {
		StringBuilder sb = new StringBuilder();
		for (char c : texto.toLowerCase().toCharArray()) { //toLowerCase convierte en minuscula. toCharArray convierte el String en un array de caracteres
			int numero;
			if (c == ' ') {
				numero = 0;
			} else {
				numero = c - 'a' + 1;
			}

			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(numero);
		}
		return sb.toString();
	}

	@Override
	public String desencriptar(String texto) {
		if (texto.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String parte : texto.split(",")) { //split corta el texto cada vez que encuentra una coma, y te devuelve un array
			int numero = Integer.parseInt(parte); // parseInt convierte el String en el número 
			if (numero == 0) {
				sb.append(' ');
			} else {
				sb.append((char) ('a' + numero - 1)); //char convierte el numero en char 
			}
		}
		return sb.toString();
	}
}
