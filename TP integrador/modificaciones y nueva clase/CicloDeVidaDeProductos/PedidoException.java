package TPintegrador.CicloDeVidaDeProductos;

public class PedidoException extends RuntimeException{ //Corta el flujo del programa ante acciones invalidas de los estados o fallos de stock
    public PedidoException(String mensaje) {
        super(mensaje);
    }
}
