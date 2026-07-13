package ar.edu.unq.po2.TPState.MP3;

public class Reproduciendo implements MP3Estado{
    protected MP3 reproductor;

    public Reproduciendo(MP3 reproductor){
        this.reproductor = reproductor;
    }

    @Override
    public void play() {
        throw new RuntimeException("Error: La canción ya se está reproduciendo.");
    }

    @Override
    public void pause() {
        this.reproductor.getCancionSeleccionada().pause();
        this.reproductor.cambiarEstado(new Pausado(this.reproductor));
    }

    @Override
    public void stop() {
        this.reproductor.getCancionSeleccionada().stop();
        this.reproductor.cambiarEstado(new SeleccionDeCancion(this.reproductor));
    }
}
