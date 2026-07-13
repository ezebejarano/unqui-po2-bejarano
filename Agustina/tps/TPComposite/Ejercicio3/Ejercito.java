package ar.edu.unq.po2.TPComposite.Ejercicio3;

import java.util.ArrayList;
import java.util.List;

public class Ejercito extends Personaje{
    private List<Personaje> miembros = new ArrayList<>();

    @Override
    public void agregar(Personaje p){
        miembros.add(p);
    }

    @Override
    public void eliminar(Personaje p){
        miembros.remove(p);
    }
    
    @Override
    public void caminarA(PuntoMapa destino) {
        for (Personaje p : miembros){
            p.caminarA(destino);
        }
    }
}
