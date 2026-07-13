package ar.edu.unq.po2.TPObserver.Publicaciones;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject { // EL OBSERVABLE
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer){
        this.observers.add(observer);
    }

    public void detach(Observer observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(Articulo articulo){
        for (Observer observer : this.observers){
            observer.update(articulo);
        }
    }
}
