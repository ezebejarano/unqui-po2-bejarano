package ar.edu.unq.po2.state;

/**
 * Demo del patrón State. Se ejecuta con: Run As > Java Application.
 */
public class Main {

	public static void main(String[] args) {
		MaquinaVideoJuego maquina = new MaquinaVideoJuego();
		mostrar(maquina);                 // Apagada

		System.out.println("\n--- Camino de 1 jugador ---");
		maquina.presionarInicio();        // apagada: no hace nada
		maquina.encender();               // -> SinFichas
		mostrar(maquina);
		maquina.presionarInicio();        // cartel "ingresen fichas"
		maquina.ingresarFicha();          // -> UnaFicha
		mostrar(maquina);
		maquina.presionarInicio();        // -> Jugando (1 jugador)
		mostrar(maquina);
		maquina.terminarJuego();          // -> SinFichas
		mostrar(maquina);

		System.out.println("\n--- Camino de 2 jugadores ---");
		maquina.ingresarFicha();          // -> UnaFicha
		maquina.ingresarFicha();          // -> DosFichas
		mostrar(maquina);
		maquina.presionarInicio();        // -> Jugando (2 jugadores)
		mostrar(maquina);
		maquina.terminarJuego();          // -> SinFichas
		mostrar(maquina);
	}

	private static void mostrar(MaquinaVideoJuego maquina) {
		System.out.println(">> Estado actual: " + maquina.getEstado().nombre());
	}
}
