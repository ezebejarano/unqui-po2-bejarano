package ar.edu.unq.po2.TPTemplateAdapter.LlamadasTelefonicas;

public abstract class LlamadaTelefonica {
    private int tiempo;
    private int horaDelDia;
    public LlamadaTelefonica(int tiempo, int horaDelDia){
        this.tiempo=tiempo;
        this.horaDelDia=horaDelDia;
    }
    public int getTiempo(){ //OPERACION CONCRETA
        return this.tiempo;
    }
    public int getHoraDelDia(){ //OPERACION CONCRETA
        return this.horaDelDia;
    }
    public abstract boolean esHoraPico(); //OPERACION PRIMITIVA

    public float costoFinal(){ //TEMPLATE METHOD
        if(this.esHoraPico()){
            return this.costoNeto()*1.2f*this.getTiempo();
        }else{
            return this.costoNeto()*this.getTiempo();
        }
    }
    public float costoNeto(){ //HOOK METHOD porque ya esta implementado
        return this.getTiempo()*1;
    }
}


//A partir de las siguientes líneas de código identifique estos conceptos, si es posible:
//        ● Template Method.
//        ● Operaciones primitivas.
//        ● Operaciones concretas.
//        ● Hook Method.
