package TPintegrador.CicloDeVidaDeProductos;

import TPintegrador.CatalogoDeProductos.Producto;
import TPintegrador.MetodosDeEnvio.Pedido;

public class EstadoEnviado extends EstadoPedidoBase{ // Registro la venta o cancelo y devuelvo los productos

    @Override
    public void entregar(Pedido pedido) {
        pedido.setEstado(new EstadoEntregado());
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("Reembolso solo de productos");
        pedido.setEstado(new EstadoCancelado());
    }
}
