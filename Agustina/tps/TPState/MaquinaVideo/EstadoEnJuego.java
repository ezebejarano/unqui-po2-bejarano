package ar.edu.unq.po2.TPState.MaquinaVideo;

public class EstadoEnJuego implements MaquinaEstado {
    protected MaquinaVideo maquinaVideo;

    public EstadoEnJuego(MaquinaVideo maquinaVideo) {
        this.maquinaVideo = maquinaVideo;
    }

    @Override
    public void escendido() {

    }

    @Override
    public void ingresarFicha() {
    }

    @Override
    public void terminarJuego() {
        System.out.println("Juego terminado. Volviendo al estado inicial.");
        this.maquinaVideo.resetearFichas();
        this.maquinaVideo.cambiarEstado(new EscendidoEstado(this.maquinaVideo));
    }
}
