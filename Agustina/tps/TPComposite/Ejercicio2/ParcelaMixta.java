package ar.edu.unq.po2.TPComposite.Ejercicio2;

import java.util.List;
import java.util.ArrayList;

public class ParcelaMixta implements Parcela {
    protected List<Parcela> regiones;

    public ParcelaMixta(Parcela p1, Parcela p2, Parcela p3, Parcela p4){
        this.regiones = new ArrayList<Parcela>();
        this.regiones.add(p1);
        this.regiones.add(p2);
        this.regiones.add(p3);
        this.regiones.add(p4);
    }

    @Override
    public Double ganasAnualesSoja() {
        Double total = 0.0;
        for (Parcela p : regiones){
            total += p.ganasAnualesSoja();
        }
        return total / 4.0;
    }

    @Override
    public Double gananciasAnualesTrigo() {
        Double total = 0.0;
        for (Parcela p : regiones){
            total += p.gananciasAnualesTrigo();
        }
        return total / 4.0;
    }
}
