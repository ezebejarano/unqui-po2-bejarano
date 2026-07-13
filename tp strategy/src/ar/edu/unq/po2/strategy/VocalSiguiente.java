package ar.edu.unq.po2.strategy;

/**
 * ConcreteStrategy: reemplaza cada vocal por la siguiente (a->e, e->i, i->o,
 * o->u, u->a). Consonantes, numeros y otros caracteres quedan igual.
 * desencriptar() hace el corrimiento inverso. Conserva mayusculas/minusculas.
 */
public class VocalSiguiente implements FormaDeEncriptacion {

	private static final String VOCALES = "aeiou";
	private static final String SIGUIENTE = "eioua";

	@Override
	public String encriptar(String texto) {
		return correr(texto, VOCALES, SIGUIENTE);
	}

	@Override
	public String desencriptar(String texto) {
		return correr(texto, SIGUIENTE, VOCALES);
	}

	/** Reemplaza cada caracter de 'desde' por el de la misma posicion en 'hasta'. */
	private String correr(String texto, String desde, String hasta) {
		StringBuilder sb = new StringBuilder();
		for (char c : texto.toCharArray()) { // recorro letra por letra convierto el texto en un array
			int i = desde.indexOf(Character.toLowerCase(c)); //index-Of busca en qué posición está esa letra.Devuelve la posición y si no esta da -1
			if (i >= 0) { // si i NO es -1 → es una vocal
				char nueva = hasta.charAt(i); //charAt agarra la letra que está en la posición i, es la vocal transformada
				if (Character.isUpperCase(c)) { 
					sb.append(Character.toUpperCase(nueva)); // original mayúscula → nueva mayúscula
				} else {
					sb.append(nueva);  // no es vocal → se deja igual
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
