package ar.edu.unq.po2.tp5;

public class Servicio extends Factura {
    public Double costoUnidad;
    private int cantidad;

    public Servicio(Double costoUnidad, int cantidad){
        this.costoUnidad = costoUnidad;
        this.cantidad = cantidad; 

    }
    @Override
    public Double getMontoAPagar() {
        return costoUnidad * cantidad;
    }
}
