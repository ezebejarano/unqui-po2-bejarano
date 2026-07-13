package TPintegrador.CicloDeVidaDeProductos;

import TPintegrador.CatalogoDeProductos.ItemCatalogo;
import TPintegrador.CatalogoDeProductos.Producto;
import TPintegrador.MetodosDeEnvio.Pedido;

public class EstadoEnPreparacion extends EstadoPedidoBase{ // Una vez que estoy en preparacion, puedo enviarlo o cancelar
    @Override
    public void enviar(Pedido pedido) {
        pedido.setEstado(new EstadoEnviado());
    }

    @Override
    public void cancelar(Pedido pedido) {
        for (ItemCatalogo item : pedido.getItems()) {
            item.reponerStock();
        }
        System.out.println("Reembolso de productos + envio");
        pedido.setEstado(new EstadoCancelado());
    }
}
