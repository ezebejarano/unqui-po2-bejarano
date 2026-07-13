package ar.edu.unq.po2.tp4.src.supermercado;
import java.util.List;
import java.util.ArrayList;

public class ProductoPrimeraNecesidad extends Producto {
    public ProductoPrimeraNecesidad(String nombre, boolean esPreciosCuidado, Double precio) {
        super(nombre, esPreciosCuidado, precio);
    }

    @Override
    public Double getPrecio() {
        // Accedemos al precio que heredamos y lo multiplicamos por 0.9
        return super.getPrecio() * 0.9;
    }
}


//Para ayudar a los clientes, el supermercado quiere realizar descuentos en productos de primer necesidad. Es por eso que al calcular el precio
//de un producto de “Primera Necesidad”, se aplica un descuento del 10%. Es decir:
//precioProductoPrimeraNecesidad = precioBaseDelProducto * 0.9