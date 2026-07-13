package ar.edu.unq.po2.state;

/**
 * ConcreteState: hay 1 ficha ingresada.
 * - ingresarFicha → DosFichas
 * - presionarInicio → empieza el juego para 1 jugador → Jugando
 */
public class UnaFicha extends EstadoMaquina {

	@Override
	public void ingresarFicha(MaquinaVideoJuego maquina) {
		System.out.println("Ficha ingresada (2).");
		maquina.cambiarEstado(new DosFichas());
	}

	@Override
	public void presionarInicio(MaquinaVideoJuego maquina) {
		System.out.println("Comienza el juego para 1 jugador.");
		maquina.cambiarEstado(new Jugando());
	}

	@Override
	public String nombre() {
		return "UnaFicha";
	}
}
