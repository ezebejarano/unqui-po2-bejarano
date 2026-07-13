import java.util.List;

// Subject: Publicacion
// Observer: Suscriptor
// Concrete Observer: Aplicacion

public class Publicacion implements Notificador{
    private List<Suscriptor> suscriptores;

    public void agregarSuscriptor(Suscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    public void eliminarSuscriptor(Suscriptor suscriptor) {
        suscriptores.remove(suscriptor);
    }

    @Override
    public void notificarBajaDePrecio() {
        suscriptores.stream()
                .forEach(s -> s.bajaDePrecio());
    }

    @Override
    public void notificarCancelacionDeReserva() {
        suscriptores.stream()
                .forEach(s -> s.cancelacionReserva());
    }

    @Override
    public void notificarReserva() {
        suscriptores.stream()
                .forEach(s -> s.reserva());
    }
}
