package ar.edu.unq.po2.tp4.src.supermercado;
import java.util.List;
import java.util.ArrayList;

public class Producto {
    private String nombre;
    private boolean esPrecioCuidado;
    private Double precio;

    public Producto (String nombre, boolean esPrecioCuidado, Double precio){
        this.nombre = nombre;
        this.esPrecioCuidado = esPrecioCuidado;
        this.precio = precio;
    }

    public Double getPrecio(){
        return this.precio;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void aumentarPrecio(Double monto){
        this.precio += monto;
    }

    public boolean esPrecioCuidado() {
        return this.esPrecioCuidado;
    }
}


//De cada producto se conoce su nombre, precio, y si el mismo es parte del
//programa Precios Cuidados o no. Por defecto, el producto no es parte del programa, a menos que se especifique lo contrario.
