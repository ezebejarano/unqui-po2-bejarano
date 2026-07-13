package TPintegrador.CatalogoDeProductos;

import java.util.ArrayList;
import java.util.List;

public class Catalogo { // Es el cliente del composite
    private List<ItemCatalogo> items = new ArrayList<>();

    public List<ItemCatalogo> getItems() {
        return this.items;
    }

    public void agregarItem(ItemCatalogo item) {
        this.items.add(item);
    }

    public void eliminarItem(ItemCatalogo item) {
        this.items.remove(item);
    }
}
