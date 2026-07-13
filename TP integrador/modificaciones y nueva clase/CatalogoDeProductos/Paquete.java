package TPintegrador.CatalogoDeProductos;

import java.util.ArrayList;
import java.util.List;

public class Paquete extends ItemCatalogo { // Es mi contenedor del Composite, pueden ser muchos paquetes o productos y delega
                                            // de forma recursiva los comportamientos a sus hijos
    private List<ItemCatalogo> paquetes = new ArrayList<>();
    private float descuentoPaquete;


    public Paquete(String nombre, String descripcion, float descuentoPaquete) {
        super(nombre, descripcion);
        this.descuentoPaquete = descuentoPaquete;
    }

    public List<ItemCatalogo> getItems() { return this.paquetes; }
    public float getDescuentoPaquete() { return this.descuentoPaquete; }

    public void agregarItem(ItemCatalogo item) {
        this.getItems().add(item);
    }

    @Override
    public float getPeso() {
        float pesoTotalPaquete = 0.0f;
        for (ItemCatalogo item : this.getItems()) {
            pesoTotalPaquete += item.getPeso();
        }
        return pesoTotalPaquete;
    }

    @Override
    public float getPrecioBase() {
        float sumaBase = 0.f;
        for (ItemCatalogo item : this.getItems()) {
            sumaBase += item.getPrecioBase();
        }
        return sumaBase;
    }

    @Override
    public float getPreciofinal() {
        float sumaHijos = 0.0f;
        for (ItemCatalogo item : this.getItems()) {
            sumaHijos += item.getPreciofinal();
        }
        return (sumaHijos * (1.0f - this.getDescuentoPaquete()));
    }

    @Override
    public boolean esValido() {
        if (this.getItems().isEmpty()) {
            return false;
        }
        for (ItemCatalogo item : this.getItems()) {
            if (!item.esValido()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean tieneStock() {
        if (this.getItems().isEmpty()) {
            return false;
        }
        for (ItemCatalogo item : this.getItems()) {
            if (!item.tieneStock()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void descontarStock() {
        for (ItemCatalogo i : this.getItems()){
                i.descontarStock();
        }
    }

    @Override
    public void reponerStock() {
        for (ItemCatalogo item : this.getItems()) {
            item.reponerStock();
        }
    }

    @Override
    public boolean tieneStockEn(List<Producto> stockLocal) {
        if (this.getItems().isEmpty()) {
            return false;
        }
        for (ItemCatalogo item : this.getItems()) {
            if (!item.tieneStockEn(stockLocal)) {
                return false;
            }
        }
        return true;
    }
}