package energia;

import java.time.LocalDate;

/**
 * Medidor inteligente. Es el Context del patron Strategy: tiene una
 * EstrategiaCalculo y le delega el calculo de la tarifa. La estrategia
 * se puede cambiar en tiempo de ejecucion con cambiarEstrategia().
 */
public class Medidor extends Punto {
    private String codigoIdentificacion;
    private String direccion;
    private ZonaEnergetica zona;
    private LocalDate mesAnio;
    private float consumo;
    private EstrategiaCalculo estrategia;
    private SubsidioCorporativo subsidio;

    public Medidor(String codigoIdentificacion, String direccion, ZonaEnergetica zona,
                   LocalDate mesAnio, float consumo, EstrategiaCalculo estrategia,
                   SubsidioCorporativo subsidio) {
        this.codigoIdentificacion = codigoIdentificacion;
        this.direccion = direccion;
        this.zona = zona;
        this.mesAnio = mesAnio;
        this.consumo = consumo;
        this.estrategia = estrategia;
        this.subsidio = subsidio;
    }

    /** Cambia la forma de calculo en tiempo de ejecucion. */
    public void cambiarEstrategia(EstrategiaCalculo nuevaEstrategia) {
        this.estrategia = nuevaEstrategia;
    }

    /** Costo bruto: se lo delego a la estrategia configurada. */
    public float costoTarifa() {
        return this.estrategia.costoTarifa(this.consumo);
    }

    /** Descuento que aplica el subsidio segun la zona del medidor. */
    public float descuentoZona() {
        return this.subsidio.obtenerDescuento(this.zona);
    }

    /** Costo final del medidor: el bruto con el descuento del subsidio aplicado. */
    @Override
    public float costoTotal() {
        return this.costoTarifa() * (1 - this.descuentoZona());
    }

    /** Consumo del medidor: el que registro textualmente. */
    @Override
    public float consumoTotal() {
        return this.consumo;
    }
}
