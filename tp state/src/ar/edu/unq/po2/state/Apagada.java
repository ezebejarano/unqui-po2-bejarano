package ar.edu.unq.po2.state;

/**
 * ConcreteState: máquina apagada (estado inicial).
 * Lo único que tiene sentido es encenderla → pasa a SinFichas.
 */
public class Apagada extends EstadoMaquina {

	@Override
	public void encender(MaquinaVideoJuego maquina) {
		System.out.println("Máquina encendida.");
		maquina.cambiarEstado(new SinFichas());
	}

	@Override
	public String nombre() {
		return "Apagada";
	}
}
