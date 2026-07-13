package ar.edu.unq.po2.TPObserver.Publicaciones;

import java.util.ArrayList;
import java.util.List;

public class Sistema extends Subject{ // CONCRETE SUBJECT
    private List<Articulo> articulos = new ArrayList<>();

    public void addArticulo(Articulo articulo){
        articulos.add(articulo);
        this.notifyObservers(articulo);
    }

    public void quitarArticulo(Articulo articulo){
        articulos.remove(articulo);
    }

    public List<Articulo> getArticulos() {
        return this.articulos;
    }

}
