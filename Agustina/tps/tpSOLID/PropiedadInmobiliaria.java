package ar.edu.unq.po2.tpSOLID;

public class PropiedadInmobiliaria {
    private String descripcion;
    private String direccion;
    private Double valorFiscal;

    public PropiedadInmobiliaria(String descripcion, String direccion, Double valorFiscal) {
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.valorFiscal = valorFiscal;
    }

    public Double getValorFiscal() {
        return valorFiscal;
    }
}