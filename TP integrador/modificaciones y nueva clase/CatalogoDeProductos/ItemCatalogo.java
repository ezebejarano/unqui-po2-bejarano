package TPintegrador.CatalogoDeProductos;

import java.util.List;

public abstract class ItemCatalogo { // Es el componente abstracto del Composite
    private String nombre;
    private String descripcion;

    public ItemCatalogo(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public abstract float getPeso();
    public abstract float getPrecioBase();
    public abstract float getPreciofinal();
    public abstract boolean esValido();

    public abstract boolean tieneStock();
    public abstract void  descontarStock();
    public abstract void reponerStock();
    public abstract boolean tieneStockEn(List<Producto> stockLocal);


}
