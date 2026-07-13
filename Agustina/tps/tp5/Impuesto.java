package ar.edu.unq.po2.tp5;

public class Impuesto extends Factura{
    private Double tasaFija;

    public Impuesto(Double tasaFija){
        this.tasaFija = tasaFija;

    }
    @Override
    public Double getMontoAPagar() {
        return tasaFija;
    }
}
