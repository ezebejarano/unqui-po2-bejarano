package ar.edu.unq.po2.TestYTestDoubles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokerStatus {
    private List<Jugador> jugadores;
    private List<Carta> cartas;

    public PokerStatus() {
        this.jugadores = new ArrayList<>();
    }

    public void registrarJugador(Jugador j) {
        this.jugadores.add(j);
    }

    public String verificar(Carta c1, Carta c2, Carta c3, Carta c4, Carta c5) {
        List<Carta> cartas = List.of(c1, c2, c3, c4, c5);
        List<String> valores = new ArrayList<>();

        if (c1.tieneMismoPaloQue(c2) && c1.tieneMismoPaloQue(c3) &&
                c1.tieneMismoPaloQue(c4) && c1.tieneMismoPaloQue(c5)) {
            return "Color";
        }

        // 3. Contamos repeticiones
        boolean hayTrio = false;
        for (Carta cartaEnDedo : cartas) {
            int cantidad = 0;
            for (Carta otraCarta : cartas) {
                if (cartaEnDedo.getValor() == otraCarta.getValor()) {
                    cantidad++;
                }
            }

            if (cantidad >= 4) {
                return "Poquer";
            } // Si hay 4, cortamos y devolvemos Poquer
            if (cantidad == 3) {
                hayTrio = true;
            }  // Si hay 3, anotamos que hubo trío pero seguimos buscando póquer
        }

        if (hayTrio) {
            return "Trio";
        }
        return "Nada";
    }

}

//Ahora se desea que además de verificar, además del póquer la existencia de Color y Trio. El color se da cuando las
//cinco cartas son del mismo color y palo. Mientras que el trio se da cuando tres cartas poseen el mismo valor.
//La extensión ahora requiere que el método verificar retorne un String que puede ser Poquer, Color, Trio o Nada en caso
//que no hayan encontrado ninguna jugada.


//Supongamos una aplicación que da soporte a un juego de póker. Dicha aplicación cuenta con la clase PokerStatus
//que está encargada de verificar si en una ronda del juego, un jugador ha recibido, en un conjunto de 5 cartas, un juego
//de póquer. Un jugador obtiene póquer si de las cinco cartas recibidas, cuatro cartas iguales en su valor (ejemplo 4
//        ases, 4 reyes).
//La clase PokerStatus define el método verificar(String,String,String,String,String) el cual recibe como parámetro la
//representación de las cinco cartas y retorna un booleano que indica si hubo póquer o no.
//Para simplificar el problema cada carta cuenta de una representación formada por un String con dos o tres letras.Una
//de las letras representa el palo o figura de la carta (P = picas, C = corazones, D = diamantes, T = tréboles).
//Mientras que las otras, la numeración, del 1 al 10 más J, Q y K.Por ejemplo, el dos de picas, en el sistema se puede
//representar con el texto “2P”; mientras que la reina de Diamantes se representa con el texto “QD” y el diez de
//diamantes con el string “10D”.En este ejercicio, debe realizar el test para el método de la clase PóquerStatus que
//recibe cinco cartas codificadas en forma textual y debe indicar si entre las mismas, existe póquer o no. Asuma que la
//codificación recibida es correcta, es decir, una carta jamás tendrá un texto que no represente a una en un mazo.
//        1) Define diferentes escenarios para el método verificar.
//2) Implemente el test pedido siguiendo la metodología TDD.
 //       3) Identifique en su test los elementos: Setup, exercise, verify, teardown.
//4) Programe lo necesario para pasar el test.