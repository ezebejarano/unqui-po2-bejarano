package ar.edu.unq.po2.tp5;

public class ProductoCooperativa extends Producto{
    public ProductoCooperativa(Double precio, int stock, String nombre) {
        super(precio, stock, nombre);
    }

    @Override
    public Double getPrecio(){
        return super.getPrecio() * 0.9;
    }
}
