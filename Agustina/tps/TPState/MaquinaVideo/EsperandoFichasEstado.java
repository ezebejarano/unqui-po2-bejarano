package ar.edu.unq.po2.TPState.MaquinaVideo;

public class EsperandoFichasEstado implements MaquinaEstado {
    protected MaquinaVideo maquinaVideo;
    public EsperandoFichasEstado(MaquinaVideo maquinaVideo){
        this.maquinaVideo = maquinaVideo;
    }

    @Override
    public void escendido() {
        int cantidad = this.maquinaVideo.getFichas();

        if (cantidad == 1){
            System.out.println("Comenzando juego para 1 JUGADOR.");
            this.maquinaVideo.cambiarEstado(new EstadoEnJuego(this.maquinaVideo));
        } else if (cantidad >= 2){
            System.out.println("Comenzando juego para 2 JUGADORES.");
            this.maquinaVideo.cambiarEstado(new EstadoEnJuego(this.maquinaVideo));
        }
    }

    @Override
    public void ingresarFicha() {
        this.maquinaVideo.agregarFicha();
        System.out.println("Ficha ingresada. Total: " + this.maquinaVideo.getFichas());
    }

    @Override
    public void terminarJuego() {

    }
}
