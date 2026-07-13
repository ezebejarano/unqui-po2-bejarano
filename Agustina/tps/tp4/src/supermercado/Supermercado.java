package ar.edu.unq.po2.tp4.src.supermercado;
import java.util.List;
import java.util.ArrayList;

public class Supermercado {
    private String nombre;
    private String direccion;
    private List<Producto> productos;

    public Supermercado (String nombre, String direccion){
        this.nombre = nombre;
        this.direccion = direccion;
        this.productos = new ArrayList<>();
    }

    public int getCantidadDeProductos(){
        return this.productos.size();
    }

    public Double getPrecioTotal(){
        Double precioTotal = 0d;
        for (Producto p : productos){
            precioTotal += p.getPrecio();

        }
        return precioTotal;
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }
}

// El supermercado, del cual se conoce el nombre y la dirección, desea conocer la cantidad total de productos que comercializa y la suma total de
//los precios de los mismos.