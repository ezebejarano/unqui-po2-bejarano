package ar.edu.unq.po2.tpSOLID;

public abstract class SolicitudDeCredito {
    protected Cliente nombre;
    protected Double montoSolicitado;
    protected int plazoMeses;

    public SolicitudDeCredito(Cliente nombre, Double montoSolicitado, int plazoMeses){
        this.nombre = nombre;
        this.montoSolicitado = montoSolicitado;
        this.plazoMeses = plazoMeses;
    }

    public Double valorCuota(){
        return this.montoSolicitado / this.plazoMeses;
    }

    public abstract boolean esAceptable();
    public abstract boolean montoCuotaMenorMensual();

    public Double getMontoSolicitado() {
        return this.montoSolicitado;
    }
}
