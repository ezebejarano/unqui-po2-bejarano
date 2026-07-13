package energia;

import java.time.LocalDate;

/**
 * Demo del ejercicio 2 (Actividad 4): una red con un medidor y una subred,
 * y la subred con dos medidores. Subsidio falso = 0 para que las cuentas
 * queden limpias (en el parcial el subsidio no se implementa).
 */
public class DemoEj2 {
    public static void main(String[] args) {
        SubsidioCorporativo sinSubsidio = (zona) -> 0f;
        ZonaEnergetica zona = new ZonaEnergetica("Zona1");
        LocalDate fecha = LocalDate.of(2026, 1, 1);

        // Tres medidores (hojas), con distintas tarifas
        Medidor m1 = new Medidor("M1", "Calle 1", zona, fecha, 100f, new TarifaResidencial(), sinSubsidio);
        Medidor m2 = new Medidor("M2", "Calle 2", zona, fecha, 500f, new TarifaIndustrial(), sinSubsidio);
        Medidor m3 = new Medidor("M3", "Calle 3", zona, fecha, 200f, new TarifaResidencial(), sinSubsidio);

        // Subred con dos medidores (costo fijo 1000)
        RedDeDistribucion subred = new RedDeDistribucion("Manzana A", 1000f);
        subred.agregarPunto(m2);
        subred.agregarPunto(m3);

        // Red principal: un medidor + la subred (costo fijo 2000)
        RedDeDistribucion redPrincipal = new RedDeDistribucion("Barrio", 2000f);
        redPrincipal.agregarPunto(m1);
        redPrincipal.agregarPunto(subred);

        // Pido el calculo de forma homogenea, sin saber que hay adentro
        System.out.println("Consumo total de la red: " + redPrincipal.consumoTotal() + "  (esperado 800)");
        System.out.println("Costo total de la red:   " + redPrincipal.costoTotal()   + "  (esperado 15814.36)");
    }
}
