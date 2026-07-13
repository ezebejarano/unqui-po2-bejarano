package ar.edu.unq.po2.TPObserver.Concursos;

import java.util.List;

public class Servidor extends Subject{
    private String nombreServidor;
    private Juego juego;

    public Servidor(String nombreServidor){
        this.nombreServidor = nombreServidor;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public void intentarSumarParticipante(Participante participante) {
        if (juego.getCantidadDeParticipantes() < 5 && !juego.isPartidaFinalizada()) {
            juego.agregarParticipante(participante);
            this.attach(participante);
            System.out.println(participante.getNombre() + " fue aceptado en la partida.");

            if (juego.getCantidadDeParticipantes() == 5) {
                this.iniciarJuego();
            }
        } else {
            System.out.println("Acción no permitida: La partida ya está llena o finalizada.");
        }
    }

    private void iniciarJuego() {
        System.out.println("Hay 5 jugadores, iniciar partida");
        this.notifyObservers(juego); //Notifica a los jugadores que inicia la partida
    }

    public void sumarParticipante(Participante participante){
        juego.agregarParticipante(participante);
    }

    public List<Pregunta> preguntasDelJuego(){
        return juego.getPreguntas();
    }

    public void recibirRespuesta(Participante participante, Pregunta pregunta, String respuestaTexto) {
        if (juego.isPartidaFinalizada()) {
            System.out.println("El juego ya terminó.");
            return;
        }
        if (pregunta.esCorrecta(respuestaTexto)) {
            juego.sumarPuntoA(participante);
            System.out.println(">> ¡Correcto para " + participante.getNombre() + "!");

            // 3. Usamos STREAMS para verificar si este acierto lo consagró ganador (llegó a 5 correctas)
            boolean hayGanador = juego.getPuntajes().values().stream().anyMatch(puntos -> puntos == 5);

            if (hayGanador) {
                juego.finalizarPartida();
                System.out.println("Partida terminada, gano el jugador: " + participante.getNombre());
            } else {
                // Notificación global intermedia (Nombre del jugador + enunciado)
                System.out.println("Aviso " + participante.getNombre() + " respondio correctamente: " + pregunta.getPregunta());
            }
            // 4. Se disparan los updates para que toda la ronda se entere de los cambios
            this.notifyObservers(juego);
        } else {
            // Si se equivoca, el enunciado pide notificar ÚNICAMENTE al jugador
            System.out.println(participante.getNombre() + " tu respuesta a'" + pregunta.getPregunta() + "' no es correcta.");
        }
    }
}
