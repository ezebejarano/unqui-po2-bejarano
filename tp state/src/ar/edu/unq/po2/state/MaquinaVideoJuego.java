package ar.edu.unq.po2.state;

/**
 * Context: la máquina de video juegos. No sabe QUÉ hacer ante cada acción:
 * delega en el estado actual y deja que él decida (y que cambie de estado).
 * Arranca Apagada.
 */
public class MaquinaVideoJuego {

	private EstadoMaquina estado;

	public MaquinaVideoJuego() {
		this.estado = new Apagada();
	}

	public void cambiarEstado(EstadoMaquina nuevoEstado) {
		this.estado = nuevoEstado;
	}

	public EstadoMaquina getEstado() {
		return estado;
	}

	// --- Operaciones públicas: cada una delega en el estado actual ---

	public void encender() {
		estado.encender(this);
	}

	public void ingresarFicha() {
		estado.ingresarFicha(this);
	}

	public void presionarInicio() {
		estado.presionarInicio(this);
	}

	public void terminarJuego() {
		estado.terminarJuego(this);
	}
}
