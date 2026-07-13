package TPintegrador.CicloDeVidaDeProductos;

import TPintegrador.CatalogoDeProductos.ItemCatalogo;
import TPintegrador.CatalogoDeProductos.Producto;
import TPintegrador.MetodosDeEnvio.Pedido;

public class EstadoConfirmado extends EstadoPedidoBase{ // Aca decido si quiero ir a preparar o cancelar el pedido

    @Override
    public void preparar(Pedido pedido) {
        pedido.setEstado(new EstadoEnPreparacion());
    }

    @Override
    public void cancelar(Pedido pedido) {
        for (ItemCatalogo item : pedido.getItems()) {
            item.reponerStock();
        }
        System.out.println("Reembolso completado");
        pedido.setEstado(new EstadoCancelado());
    }
}
