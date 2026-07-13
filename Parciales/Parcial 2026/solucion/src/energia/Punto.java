package energia;

/**
 * Componente del Composite. Tanto un Medidor (hoja) como una
 * RedDeDistribucion (compuesto) son un Punto y responden de forma
 * homogenea a costoTotal() y consumoTotal().
 */
public abstract class Punto {
    public abstract float costoTotal();
    public abstract float consumoTotal();
}
