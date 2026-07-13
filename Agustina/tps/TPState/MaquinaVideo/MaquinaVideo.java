package ar.edu.unq.po2.TPState.MaquinaVideo;

public  class MaquinaVideo {
    protected MaquinaEstado estado;
    protected int fichas;

    public MaquinaVideo(){
        this.estado = new EscendidoEstado(this);
        this.fichas = 0;
    }

    public void presionarInicio(){ this.estado.escendido();}
    public void ingresarFicha(){ this.estado.ingresarFicha();}
    public void terminarJuego(){this.estado.terminarJuego();}

    public void cambiarEstado(MaquinaEstado nuevoEstado){
        this.estado = nuevoEstado;
    }

    public void agregarFicha() {
        this.fichas++;
    }

    public void resetearFichas() {
        this.fichas = 0;
    }

    public int getFichas(){
        return this.fichas;
    }
}
