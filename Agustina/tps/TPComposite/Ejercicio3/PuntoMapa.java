package ar.edu.unq.po2.TPComposite.Ejercicio3;

import ar.edu.unq.po2.tp3.Punto;

public class PuntoMapa extends Punto {
    private boolean tieneLaja;

    public PuntoMapa(float x, float y) {
        super(x, y);
        this.tieneLaja = false;
    }

    public PuntoMapa(int x, int y,boolean valor) {
        super(x,y);
        this.tieneLaja = valor;
    }

    public boolean tieneLaja(){
        return this.tieneLaja;
    }

    void setTieneLaja(boolean valor){
        this.tieneLaja = valor;
    }
}
