package energia;

import java.util.ArrayList;
import java.util.List;

/**
 * Compuesto del Composite: agrupa varios Puntos, que pueden ser medidores
 * u otras redes. Calcula costo y consumo delegando recursivamente en sus hijos.
 */
public class RedDeDistribucion extends Punto {
    private String nombre;
    private float costoFijo;                       // costo fijo de mantenimiento propio
    private List<Punto> puntos = new ArrayList<Punto>();

    public RedDeDistribucion(String nombre, float costoFijo) {
        this.nombre = nombre;
        this.costoFijo = costoFijo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void agregarPunto(Punto punto) {
        this.puntos.add(punto);
    }

    public void removerPunto(Punto punto) {
        this.puntos.remove(punto);
    }

    /** Consumo total: la suma del consumo de todos sus elementos. */
    @Override
    public float consumoTotal() {
        float total = 0f;
        for (Punto punto : this.puntos) {
            total += punto.consumoTotal();
        }
        return total;
    }

    /**
     * Costo total: la suma del costo de sus elementos, mas su costo fijo de
     * mantenimiento, mas un 3% de recargo sobre la suma de los elementos.
     */
    @Override
    public float costoTotal() {
        float sumaElementos = 0f;
        for (Punto punto : this.puntos) {
            sumaElementos += punto.costoTotal();
        }
        return sumaElementos + this.costoFijo + (sumaElementos * 0.03f);
    }
}
