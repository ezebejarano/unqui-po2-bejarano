package ar.edu.unq.po2.state;

/**
 * ConcreteState: encendida, esperando fichas (el "momento inicial").
 * - ingresarFicha → UnaFicha
 * - presionarInicio → muestra el cartel "ingresen fichas" (no cambia de estado)
 */
public class SinFichas extends EstadoMaquina {

	@Override
	public void ingresarFicha(MaquinaVideoJuego maquina) {
		System.out.println("Ficha ingresada (1).");
		maquina.cambiarEstado(new UnaFicha());
	}

	@Override
	public void presionarInicio(MaquinaVideoJuego maquina) {
		System.out.println("Cartel: ingresen fichas.");
	}

	@Override
	public String nombre() {
		return "SinFichas";
	}
}
