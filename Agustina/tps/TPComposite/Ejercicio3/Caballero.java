package ar.edu.unq.po2.TPComposite.Ejercicio3;

public class Caballero extends Personaje {
    private PuntoMapa actual;
    private boolean subiendo = true; // Para controlar el zigzag

    @Override
    public void caminarA(PuntoMapa destino) {
        while (!this.actual.equals(destino)) {
            this.avanzarConZigZag(destino);
        }
    }

    private void avanzarConZigZag(PuntoMapa destino) {
        // 1. Avanza en X hacia el destino
        if (actual.getX() < destino.getX()) actual.setX(actual.getX() + 1);

        // 2. Hace el zigzag en Y
        if (subiendo) {
            actual.setY(actual.getY() + 1);
        } else {
            actual.setY(actual.getY() - 1);
        }
        this.subiendo = !this.subiendo; // Cambia la dirección para el próximo paso
    }
}