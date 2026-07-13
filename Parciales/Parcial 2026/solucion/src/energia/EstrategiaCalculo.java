package energia;

/**
 * Strategy: contrato comun de todas las formas de calcular la tarifa.
 * Cada tarifa concreta encapsula su propio algoritmo de costo.
 */
public interface EstrategiaCalculo {
    float costoTarifa(float consumo);
}
