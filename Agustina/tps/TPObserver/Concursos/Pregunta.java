package ar.edu.unq.po2.TPObserver.Concursos;

public class Pregunta {
    private String pregunta;
    private String respuestaPregunta;

    public Pregunta(String pregunta, String respuestaPregunta){
        this.pregunta = pregunta;
        this.respuestaPregunta = respuestaPregunta;
    }

    public String getPregunta(){
        return this.pregunta;
    }

    public boolean esCorrecta(String respuestaDelJugador) {
        return this.respuestaPregunta.equalsIgnoreCase(respuestaDelJugador);
    }
}
