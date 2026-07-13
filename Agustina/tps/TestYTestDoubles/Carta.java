package ar.edu.unq.po2.TestYTestDoubles;

public class Carta {
    private int valor; // 1 al 13 (donde 11=J, 12=Q, 13=K)
    private String palo; // "P", "C", "D", "T"

    public Carta(int valor, String palo){
        this.valor = valor;
        this.palo = palo;
    }

    public int getValor() {
        return valor;
    }

    public String getPalo() {
        return palo;
    }

    public boolean esSuperiorA(Carta otraCarta) {
        return this.valor > otraCarta.getValor();
    }
    
    public boolean tieneMismoPaloQue(Carta otraCarta) {
        return this.palo.equals(otraCarta.getPalo());
    }
}

//Los desarrolladores del póquer se dieron cuenta que la representación de las cartas usando strings es poco útil ya que
//ahora quieren comparar las cartas para saber cual es mayor que otra. Por eso deben implementar una clase Carta que
//representa a las cartas de un mazo. De una carta se puede conocer el valor y el palo. Por ejemplo, 4C ahora se
//representaría con una instancia de Carta. También debe ser posible saber si el valor de una carta es superior a otra, y
//si poseen el mismo palo.

