package ar.edu.unq.po2.TPComposite.Ejercicio3;

import ar.edu.unq.po2.tp3.Punto;

import java.util.List;

public class Ingeniero extends Personaje {
    private int cantidadLajas;
    private PuntoMapa actual;

    public Ingeniero(int lajasIniciales, PuntoMapa inicio) {
        this.cantidadLajas = lajasIniciales;
        this.actual = inicio;
    }

    @Override
    public void caminarA(PuntoMapa destino) {
        while (!this.actual.equals(destino)) {
            this.dejarLajaSiCorresponde();
            this.avanzarHacia(destino);
        }
    }

    private void dejarLajaSiCorresponde() {
        // Solo pone si tiene lajas y el punto actual NO tiene una ya puesta
        if (this.cantidadLajas > 0 && !this.actual.tieneLaja()) {
            this.actual.setTieneLaja(true);
            this.cantidadLajas--;
        }
    }

    private void avanzarHacia(PuntoMapa destino) {
        float actualX = this.actual.getX();
        float actualY = this.actual.getY();

        // Si todavía no llegó en X, avanza un paso
        if (actualX < destino.getX()) {
            this.actual.setX(actualX + 1);
        } else if (actualX > destino.getX()) {
            this.actual.setX(actualX - 1);
        }
        // Si ya llegó en X pero falta en Y, avanza en Y
        else if (actualY < destino.getY()) {
            this.actual.setY(actualY + 1);
        } else if (actualY > destino.getY()) {
            this.actual.setY(actualY - 1);
        }
    }
}