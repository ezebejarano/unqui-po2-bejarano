package ar.edu.unq.po2.TPState.MP3;

public class MP3 {
    protected MP3Estado estado;
    protected Song cancionSeleccionada;

    public MP3(Song cancion){
        this.cancionSeleccionada = cancion;
        this.estado = new SeleccionDeCancion(this);
    }

    public void play(){ this.estado.play();}
    public void pause(){ this.estado.pause();}
    public void stop(){this.estado.stop();}

    public void cambiarEstado(MP3Estado nuevoEstado) {
        this.estado = nuevoEstado;
    }
    public Song getCancionSeleccionada() {
        return this.cancionSeleccionada;
    }
}
