package energia;

/**
 * ConcreteStrategy: cargo fijo de $500 mas $12 por cada kWh consumido.
 */
public class TarifaResidencial implements EstrategiaCalculo {

    @Override
    public float costoTarifa(float consumo) {
        return 500f + (12f * consumo);
    }
}
