package ar.edu.unq.po2.TPObserver.Concursos;

import ar.edu.unq.po2.TPObserver.EncuentrosDeportivos.Partido;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Juego {
    private List<Participante> participantes = new ArrayList<>();
    private List<Pregunta> preguntas;
    private boolean partidaFinalizada = false;
    private Map<Participante, Integer> puntajes = new HashMap<>();

    public Juego(List<Pregunta> preguntas){
        this.preguntas = preguntas;
    }

    public int getCantidadDeParticipantes(){
        return participantes.size();
    }

    public void agregarParticipante(Participante participante){
        this.participantes.add(participante);
        this.puntajes.put(participante, 0);
    }

    public void sumarPuntoA(Participante participante){
        int puntosActuales = this.puntajes.get(participante);
        this.puntajes.put(participante, puntosActuales + 1);
    }

    public Map<Participante, Integer> getPuntajes() {
        return puntajes;
    }

    public List<Pregunta> getPreguntas(){
        return preguntas;
    }

    public List<Participante> getParticipantes(){
        return participantes;
    }

    public boolean isPartidaFinalizada(){
        return partidaFinalizada;
    }

    public void finalizarPartida(){
        this.partidaFinalizada = true;
    }
}
