package energia;

/**
 * Zona energetica del medidor. Se usa para consultar el subsidio.
 */
public class ZonaEnergetica {
    private String nombre;

    public ZonaEnergetica(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
