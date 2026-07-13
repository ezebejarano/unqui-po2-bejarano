package ar.edu.unq.po2.tpSOLID;

public class SolicitudDeCreditoPersonal extends SolicitudDeCredito{
    public SolicitudDeCreditoPersonal(Cliente nombre, Double montoSolicitado, int plazoMeses) {
        super(nombre, montoSolicitado, plazoMeses);
    }

    @Override
    public boolean esAceptable() {
        return (nombre.sueldoNetoAnual() >= 15000) && this.montoCuotaMenorMensual();
    }

    @Override
    public boolean montoCuotaMenorMensual() {
        return this.valorCuota() < nombre.getSueldoNetoMensual() * 0.70;
    }
}

