package ar.edu.unq.po2.TPObserver.Concursos;

import java.util.List;

public class Participante implements Observer{
    private String nombre;
    private boolean estaJugando = false;
    private Servidor servidor;
    private List<Pregunta> preguntasDeLaPartida;


    public void solicitarUnirseAPartida(Servidor servidor) {
        servidor.intentarSumarParticipante(this);
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEstaJugando() {
        return estaJugando;
    }

    public List<Pregunta> getPreguntasDeLaPartida() {
        return preguntasDeLaPartida;
    }

    @Override
    public void update(Juego juego) {
        if (!this.estaJugando && !juego.isPartidaFinalizada()) {
            this.estaJugando = true;
            this.preguntasDeLaPartida = juego.getPreguntas();
            System.out.println("[" + nombre + "] Comenzo el juego, recibi las " + juego.getPreguntas().size() + " preguntas.");
            return;
        }

        // Evento 2: El juego terminó, nos enteramos y nos apagamos
        if (juego.isPartidaFinalizada()) {
            this.estaJugando = false;
            System.out.println("[" + nombre + "] El juego ha terminado. Desconectando...");
        }
    }

    public void enviarRespuesta(Pregunta pregunta, String textoRespuesta) {
        if (!this.estaJugando) {
            System.out.println("Acción no permitida: " + nombre + " no puede responder (juego no iniciado o ya terminó).");
            return;
        }
        this.servidor.recibirRespuesta(this, pregunta, textoRespuesta);
    }
}
