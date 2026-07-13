package ar.edu.unq.po2.tp5;

public class Producto {
    private Double precio;
    private int stock;
    private String nombre;

    public Producto (Double precio, int stock, String nombre){
        this.precio = precio;
        this.stock = stock;
        this.nombre = nombre;
    }

    public int getStock(){
        return this.stock;
    }
    public void decrementarStock(){
        this.stock = stock - 1;
    }

    public Double getPrecio(){
        return this.precio;
    }
}
