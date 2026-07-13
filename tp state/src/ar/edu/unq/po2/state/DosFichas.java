package ar.edu.unq.po2.state;

/**
 * ConcreteState: hay 2 fichas ingresadas (el máximo).
 * - presionarInicio → empieza el juego para 2 jugadores → Jugando
 * - ingresarFicha → no hace nada (ya hay 2 fichas; usa el comportamiento
 *   por defecto heredado de EstadoMaquina)
 */
public class DosFichas extends EstadoMaquina {

	@Override
	public void presionarInicio(MaquinaVideoJuego maquina) {
		System.out.println("Comienza el juego para 2 jugadores.");
		maquina.cambiarEstado(new Jugando());
	}

	@Override
	public String nombre() {
		return "DosFichas";
	}
}
