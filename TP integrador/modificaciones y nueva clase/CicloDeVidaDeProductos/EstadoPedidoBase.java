package TPintegrador.CicloDeVidaDeProductos;

import TPintegrador.CatalogoDeProductos.ItemCatalogo;
import TPintegrador.CatalogoDeProductos.Producto;
import TPintegrador.MetodosDeEnvio.Pedido;

public abstract class EstadoPedidoBase implements EstadoPedido { // Se hace esta clase lanzando como base los exception y asi bloquear
                                                                 // acciones que sean invalidas, cambiando donde corresponda cada una 
    @Override
    public void agregarItem(Pedido pedido, ItemCatalogo item) {
        throw new PedidoException("La operacion es invalidada para el estado actual");
    }
    @Override
    public void quitarItem(Pedido pedido, ItemCatalogo item) {
        throw new PedidoException("La operacion es invalidada para el estado actual");
    }
    @Override
    public void confirmar(Pedido pedido) {
        throw new PedidoException("La operacion es invalidada para el estado actual");
    }
    @Override
    public void preparar(Pedido pedido) {
        throw new PedidoException("La operacion es invalidada para el estado actual");
    }
    @Override
    public void enviar(Pedido pedido) {
        throw new PedidoException("La operacion es invalidada para el estado actual");
    }
    @Override
    public void entregar(Pedido pedido) {
        throw new PedidoException("La operacion es invalidada para el estado actual");
    }
    @Override
    public void cancelar(Pedido pedido) {
        throw new PedidoException("La operacion es invalidad para el estado actual");
    }

}
