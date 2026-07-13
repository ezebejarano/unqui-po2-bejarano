package ar.edu.unq.po2.state;

/**
 * State (clase abstracta): declara las operaciones que entiende la máquina.
 * Por defecto NO hacen nada; cada estado concreto redefine solo las que le
 * corresponden (así no hay que escribir métodos vacíos en cada estado).
 */
public abstract class EstadoMaquina {

	public void encender(MaquinaVideoJuego maquina) {
		// por defecto no hace nada
	}

	public void ingresarFicha(MaquinaVideoJuego maquina) {
		// por defecto no hace nada
	}

	public void presionarInicio(MaquinaVideoJuego maquina) {
		// por defecto no hace nada
	}

	public void terminarJuego(MaquinaVideoJuego maquina) {
		// por defecto no hace nada
	}

	/** Nombre del estado, útil para mostrar y para los tests. */
	public abstract String nombre();
}
