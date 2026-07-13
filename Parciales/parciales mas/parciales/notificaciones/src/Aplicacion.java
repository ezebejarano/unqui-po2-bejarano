public class Aplicacion implements Suscriptor {
    private Aviso aviso;

    @Override
    public void bajaDePrecio() {
        aviso.avisar("Bajo el precio");
    }

    @Override
    public void cancelacionReserva() {}

    @Override
    public void reserva() {}
}
