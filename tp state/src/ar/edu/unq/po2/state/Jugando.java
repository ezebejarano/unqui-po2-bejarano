package ar.edu.unq.po2.state;

/**
 * ConcreteState: el juego está en curso (1 o 2 jugadores).
 * - terminarJuego → vuelve al momento inicial (SinFichas)
 * El resto de las acciones no hacen nada durante el juego.
 */
public class Jugando extends EstadoMaquina {

	@Override
	public void terminarJuego(MaquinaVideoJuego maquina) {
		System.out.println("Juego terminado. Vuelve al inicio.");
		maquina.cambiarEstado(new SinFichas());
	}

	@Override
	public String nombre() {
		return "Jugando";
	}
}
