package ar.edu.unq.po2.TPTemplateAdapter.AyudandoAlSoberano;

public class CuentaCorriente extends CuentaBancaria{
    private int descubierto;
    public CuentaCorriente(String titular, int descubierto){
        super(titular);
        this.descubierto=descubierto;
    }
    public int getDescubierto(){
        return this.descubierto;
    }

    @Override
    public boolean sePuedeExtraer(int monto) {
        if(this.getSaldo()+this.getDescubierto()>=monto){
        return true;
    }
        return false;
    }
}
