package ar.edu.unq.po2.TPObserver.Publicaciones;

public class Investigador implements Observer{ // CONCRETE OBSERVER
    private String nombre;
    private String tipoDeInteres = "Smaltalk";
    private int notificacionesRecibidas = 0;

    public Investigador(String nombre, String tipoDeInteres, String lugarDeInteres){
        this.nombre = nombre;
        this.tipoDeInteres = tipoDeInteres;
    }

    public int getNotificacionesRecibidas() {
        return this.notificacionesRecibidas;
    }

    @Override
    public void update(Articulo articulo) {
        boolean coincideTipo = articulo.getTipo().equalsIgnoreCase(this.tipoDeInteres);

        if (coincideTipo) {
            this.notificacionesRecibidas++;
            System.out.println("Notificación para " + this.nombre + ": Se publicó un artículo de interés (" + articulo.getTitulo() + ")");
        }
    }
}
