package ar.edu.unq.po2.strategy;

/**
 * Demo de uso del patron Strategy. Se ejecuta con: Run As > Java Application.
 */
public class Main {

	public static void main(String[] args) {
		// Forma por defecto: orden de palabras
		EncriptadorNaive enc = new EncriptadorNaive(new OrdenPalabras());
		String o1 = enc.encriptar("hola mundo cruel");
		System.out.println("OrdenPalabras   -> " + o1);
		System.out.println("  desencriptado -> " + enc.desencriptar(o1));

		// Cambio de forma EN TIEMPO DE EJECUCION: vocal siguiente
		enc.setForma(new VocalSiguiente());
		String o2 = enc.encriptar("murcielago");
		System.out.println("VocalSiguiente  -> " + o2);
		System.out.println("  desencriptado -> " + enc.desencriptar(o2));

		// Otra forma mas: letra a numero
		enc.setForma(new LetraANumero());
		String o3 = enc.encriptar("diego");
		System.out.println("LetraANumero    -> " + o3);
		System.out.println("  desencriptado -> " + enc.desencriptar(o3));
	}
}
