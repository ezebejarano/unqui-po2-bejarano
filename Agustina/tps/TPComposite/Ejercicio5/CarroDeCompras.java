package ar.edu.unq.po2.TPComposite.Ejercicio5;

import java.util.ArrayList;
import java.util.List;

public class CarroDeCompras {
    private List<Producto> productos = new ArrayList<>();

    private List<Producto> setElements(List<Producto> productos){
        return this.productos = productos;
    }

    public List<Producto> getElements(){
        return productos;
    }


    public float total(){
        float total = 0;
        for (Producto p : productos){
            total += p.getPrice();
        }
        return total;
    }

    public int totalRounded(){
        return Math.round(this.total());
    }
}
