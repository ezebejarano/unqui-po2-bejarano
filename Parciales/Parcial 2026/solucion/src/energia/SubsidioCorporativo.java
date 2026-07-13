package energia;

/**
 * Interfaz externa (dada por el enunciado): la usamos pero NO la implementamos.
 * Devuelve el porcentaje de descuento (ej: 0.15 para 15%) segun la zona.
 * Si la zona no tiene subsidio, devuelve 0.
 */
public interface SubsidioCorporativo {
    float obtenerDescuento(ZonaEnergetica zona);
}
