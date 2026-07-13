package ar.edu.unq.po2.TPState.MP3;

public class SeleccionDeCancion implements MP3Estado {
    protected MP3 reproductor;

    public SeleccionDeCancion(MP3 reproductor) {
        this.reproductor = reproductor;
    }

    @Override
    public void play() {
        this.reproductor.getCancionSeleccionada().play();
        this.reproductor.cambiarEstado(new Reproduciendo(this.reproductor));
    }

    @Override
    public void pause() {
        throw new RuntimeException("Error: No hay ninguna canción reproduciéndose para pausar.");
    }

    @Override
    public void stop() {
    }
}