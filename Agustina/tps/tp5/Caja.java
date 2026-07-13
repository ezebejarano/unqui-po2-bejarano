package ar.edu.unq.po2.tp5;

import java.util.List;

public class Caja {
    private Double montoAPagar = 0d;
    private Agencia agencia;

    public Caja(Agencia agencia){
        this.agencia = agencia;
    }
    public void registrarProducto(Producto producto) {
        if (producto.getStock() > 0) {
            this.montoAPagar += producto.getPrecio();
            producto.decrementarStock();
        } else {
            System.out.println("No hay stock");
        }
    }

    public void registrarFactura(Factura factura){
        this.montoAPagar += factura.getMontoAPagar();
        factura.registrarEnAgencia(this.agencia);
    }

    public Double getMontoAPagar(){
        return montoAPagar;
    }


}

