package energia;

/**
 * ConcreteStrategy: sin cargo fijo.
 * Hasta 1000 kWh se cobran a $15. Si el consumo supera los 1000,
 * los primeros 1000 van a $15 y el excedente (lo que pasa de 1000) a $25.
 */
public class TarifaIndustrial implements EstrategiaCalculo {

    @Override
    public float costoTarifa(float consumo) {
        if (consumo <= 1000f) {
            return 15f * consumo;
        } else {
            float excedente = consumo - 1000f;
            return (15f * 1000f) + (25f * excedente);
        }
    }
}
