package ar.edu.unq.po2.TPObserver.EncuentrosDeportivos;

import java.util.List;

public class ServidorInteresados implements Observer{
    private String nombre;
    private List<String> deportesDeInteres;
    private int alertasRecibidas = 0;

    public ServidorInteresados(String nombre, List<String> deportesDeInteres){
        this.nombre = nombre;
        this.deportesDeInteres = deportesDeInteres;
    }

    @Override
    public void update(Partido partido) {
        if (this.deportesDeInteres.contains(partido.getDeporte())){
            this.alertasRecibidas++;
            System.out.println("[" + this.nombre + "] Guardando resultado:" + partido.getResultado());
        }
    }

    public int getAlertasRecibidas() {
        return this.alertasRecibidas;
    }
}
