package ar.edu.unq.po2.TPObserver.EncuentrosDeportivos;

public interface Observer {
    public void update(Partido partido);
}


// las aplicaciones moviles tienen diferentes suscripciones, el servidor solamente le manda mensaje cuando se
// actualiza una de esas suscripciones
// los servidores pueden estan suscriptos a diferentes deportes
// los servidores y las aplicaciones son lo mismo