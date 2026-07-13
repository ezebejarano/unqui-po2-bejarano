package ar.edu.unq.po2.TPObserver.EncuentrosDeportivos;

import ar.edu.unq.po2.TPObserver.Publicaciones.Articulo;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer){
        this.observers.add(observer);
    }

    public void detach(Observer observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(Partido partido){
        for (Observer observer : this.observers){
            observer.update(partido);
        }
    }
}
