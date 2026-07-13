package ar.edu.unq.po2.tpSOLID;

public class SolicitudDeCreditoHipotecario extends SolicitudDeCredito{
    private PropiedadInmobiliaria propiedad;
    public SolicitudDeCreditoHipotecario(Cliente nombre, Double montoSolicitado, int plazoMeses, PropiedadInmobiliaria propiedad) {
        super(nombre, montoSolicitado, plazoMeses);
        this.propiedad = propiedad;
    }

    @Override
    public boolean esAceptable() {
        int edadAlFinalizar = nombre.getEdad() + (this.plazoMeses / 12);
        return (edadAlFinalizar <= 65) && this.montoCuotaMenorMensual() && montoMenorValorGarantia() ;
    }

    @Override
    public boolean montoCuotaMenorMensual() {
        return this.valorCuota() < nombre.getSueldoNetoMensual() * 0.50;
    }

    public boolean montoMenorValorGarantia(){
        return this.montoSolicitado < this.propiedad.getValorFiscal() * 0.50;
    }
}
