package ar.edu.unq.po2.TPObserver.EncuentrosDeportivos;

import java.util.ArrayList;
import java.util.List;

public class CentroDeportivo extends Subject{
    private List<Partido> partidos = new ArrayList<>();


    public void registrarPartido(Partido partido) {
        this.partidos.add(partido);
        this.notifyObservers(partido);
    }

    public List<Partido> getPartidos(){
        return partidos;
    }
}

