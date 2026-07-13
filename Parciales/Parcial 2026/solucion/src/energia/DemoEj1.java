package energia;

import java.time.LocalDate;

/**
 * Demo SOLO para probar el ejercicio 1 (en el parcial no se implementa el subsidio).
 */
public class DemoEj1 {
    public static void main(String[] args) {
        ZonaEnergetica metro = new ZonaEnergetica("AreaMetropolitana");

        // Subsidio falso: 15% en la zona metropolitana, 0 en el resto.
        SubsidioCorporativo subsidioFalso = (zona) -> {
            if (zona.getNombre().equals("AreaMetropolitana")) { return 0.15f; }
            else { return 0f; }
        };

        // Medidor residencial, consumo 100 kWh
        Medidor m = new Medidor("MED-1", "Calle 1", metro, LocalDate.of(2026, 1, 1),
                100f, new TarifaResidencial(), subsidioFalso);

        System.out.println("Residencial -> costo bruto: " + m.costoTarifa() + "  (esperado 1700)");
        System.out.println("Residencial -> costo total: " + m.costoTotal() + "  (esperado 1445 = 1700 x 0.85)");

        // Cambio la estrategia EN RUNTIME a industrial
        m.cambiarEstrategia(new TarifaIndustrial());
        System.out.println("Industrial(100) -> costo bruto: " + m.costoTarifa() + "  (esperado 1500)");

        // Industrial con consumo 1200 (se pasa de 1000): 15*1000 + 25*200
        Medidor grande = new Medidor("MED-2", "Calle 2", new ZonaEnergetica("Interior"),
                LocalDate.of(2026, 1, 1), 1200f, new TarifaIndustrial(), subsidioFalso);
        System.out.println("Industrial(1200) -> costo bruto: " + grande.costoTarifa() + "  (esperado 20000)");
    }
}
