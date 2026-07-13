package ar.edu.unq.po2.TPState.MP3;

public class Pausado implements MP3Estado {
    protected MP3 reproductor;

    public Pausado(MP3 reproductor) {
        this.reproductor = reproductor;
    }

    @Override
    public void play() {
        this.reproductor.getCancionSeleccionada().play();
        this.reproductor.cambiarEstado(new Reproduciendo(this.reproductor));
    }

    @Override
    public void pause() {
        this.reproductor.getCancionSeleccionada().play();
        this.reproductor.cambiarEstado(new Reproduciendo(this.reproductor));
    }

    @Override
    public void stop() {
        this.reproductor.getCancionSeleccionada().stop();
        this.reproductor.cambiarEstado(new SeleccionDeCancion(this.reproductor));
    }
}
