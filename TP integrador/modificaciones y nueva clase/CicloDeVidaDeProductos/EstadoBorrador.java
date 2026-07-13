package TPintegrador.CicloDeVidaDeProductos;

import TPintegrador.CatalogoDeProductos.ItemCatalogo;
import TPintegrador.CatalogoDeProductos.Producto;
import TPintegrador.MetodosDeEnvio.Pedido;

public class EstadoBorrador implements EstadoPedido{ // Se puede agregar o quitar items, cuando confirmo voy a validar el stock
                                                     // en el composite y el template method se encarga del metodo de pago
    @Override
    public void agregarItem(Pedido pedido, ItemCatalogo item) {
        pedido.getItems().add(item);
    }

    @Override
    public void quitarItem(Pedido pedido, ItemCatalogo item) {
        pedido.getItems().remove(item);
    }

    @Override
    public void confirmar(Pedido pedido) {
        for (ItemCatalogo item : pedido.getItems()) {
            if (!item.tieneStock()) {
                throw new PedidoException("No hay stock suficiente para" + item.getNombre());
            }
        }

        float montoTotal = pedido.getPrecioTotalProductos() + pedido.getCostoDeEnvio();
        pedido.getMetodoDePago().pagar(montoTotal);

        for (ItemCatalogo item : pedido.getItems()) {
            item.descontarStock();
        }
        pedido.setEstado(new EstadoConfirmado());
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new PedidoException("No se puede preparar un pedido en borrador");
    }

    @Override
    public void enviar(Pedido pedido) {
        throw new PedidoException("No se puede enviar un pedido en borrador");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new PedidoException("No se puede entregar un pedido en borrador");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstado(new EstadoCancelado());
    }
}
