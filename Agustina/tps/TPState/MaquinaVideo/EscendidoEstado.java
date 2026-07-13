package ar.edu.unq.po2.TPState.MaquinaVideo;

public class EscendidoEstado implements MaquinaEstado {
    protected MaquinaVideo maquinaVideo;

    public EscendidoEstado(MaquinaVideo maquinaVideo){
        this.maquinaVideo = maquinaVideo;
    }

    @Override
    public void escendido() {
        System.out.println("Ingresar Fichas");
    }

    @Override
    public void ingresarFicha() {
        this.maquinaVideo.agregarFicha();
        System.out.println("Ficha ingresada. Total: " + this.maquinaVideo.getFichas());
        this.maquinaVideo.cambiarEstado(new EsperandoFichasEstado(this.maquinaVideo));

    }

    @Override
    public void terminarJuego() {
    }
}
