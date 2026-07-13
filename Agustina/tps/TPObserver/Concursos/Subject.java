package ar.edu.unq.po2.TPObserver.Concursos;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer){
        this.observers.add(observer);
    }

    public void detach(Observer observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(Juego juego){
        for (Observer observer : this.observers){
            observer.update(juego);
        }
    }

}
