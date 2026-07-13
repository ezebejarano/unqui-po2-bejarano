package TPintegrador.CicloDeVidaDeProductos;

import TPintegrador.CatalogoDeProductos.ItemCatalogo;
import TPintegrador.CatalogoDeProductos.Producto;
import TPintegrador.MetodosDeEnvio.Pedido;

public interface EstadoPedido { //Es la interfaz de los estados, aca se declaran las diferentes transiciones
    public void agregarItem(Pedido pedido, ItemCatalogo item);
    public void quitarItem(Pedido pedido, ItemCatalogo item);
    public void confirmar(Pedido pedido);
    public void preparar(Pedido pedido);
    public void enviar(Pedido pedido);
    public void entregar(Pedido pedido);
    public void cancelar(Pedido pedido);
}
