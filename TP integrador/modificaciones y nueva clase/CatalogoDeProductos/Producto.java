package TPintegrador.CatalogoDeProductos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Producto extends ItemCatalogo { // Es la hoja de mi Composite. Recibe los comportamientos de ItemCatalogo
    private String sku;
    private String marca;
    private String categoria;
    private float precio;
    private float precioFinal;
    private float peso;
    private int stock;

    private Map<String, Object> atributosDinamicos = new HashMap<>();

    public Producto(String sku, String nombre, String descripcion, String marca, String categoria, float precio, float precioFinal, float peso, int stock) {
        super(nombre, descripcion);
        this.sku = sku;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
        this.precioFinal = precioFinal;
        this.peso = peso;
        this.stock = stock;
    }

    public String getSku() { return this.sku; }
    public String getMarca() { return this.marca; }
    public String getCategoria() { return this.categoria; }
    public float getPrecio() { return this.precio; }

    public Map<String, Object> getAtributosDinamicos() { return this.atributosDinamicos; }
    public int getStock(){return this.stock;}

    public void agregarAtributoDinamico(String clave, Object valor) {
        this.getAtributosDinamicos().put(clave, valor);
    }

    @Override
    public float getPeso() {
        return this.peso;
    }

    @Override
    public float getPrecioBase() {
        return this.getPrecio();
    }

    @Override
    public float getPreciofinal() {

        return this.precioFinal;
    }

    @Override
    public boolean esValido() {
        if (this.getNombre() == null || this.getNombre().isEmpty() || this.getSku() == null || this.getSku().isEmpty()) {
            return false;
        }
        for (Object valor : this.getAtributosDinamicos().values()) {
            if (valor == null) {
                return false;
            }
        }
        return true;
    }

    public void restarUnidadStock() {
        if (this.stock > 0) {
            this.stock--;
        }
    }

    public void sumarUnidadStock() {
        this.stock++;
    }

    @Override
    public boolean tieneStock() {
        return this.getStock() > 0;
    }

    @Override
    public void descontarStock() {
        this.restarUnidadStock();
    }

    @Override
    public void reponerStock() {
        this.sumarUnidadStock();
    }

    @Override
    public boolean tieneStockEn(List<Producto> stockLocal) {
        return stockLocal.contains(this) && this.getStock() > 0;
    }
}



