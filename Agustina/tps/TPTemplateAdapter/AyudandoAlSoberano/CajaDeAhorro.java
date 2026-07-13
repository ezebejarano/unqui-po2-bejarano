package ar.edu.unq.po2.TPTemplateAdapter.AyudandoAlSoberano;

public class CajaDeAhorro extends CuentaBancaria{
    private int limite;
    public CajaDeAhorro(String titular, int limite){
        super(titular);
        this.limite=limite;
    }
    public int getLimite(){
        return this.limite;
    }

    @Override
    public boolean sePuedeExtraer(int monto) {
        if(this.getSaldo()>=monto && this.getLimite()>=monto){
            return true;
        } return false;
    }
}
