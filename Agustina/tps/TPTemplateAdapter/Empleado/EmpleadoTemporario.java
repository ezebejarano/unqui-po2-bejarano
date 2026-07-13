package ar.edu.unq.po2.TPTemplateAdapter.Empleado;

public class EmpleadoTemporario extends Empleado {
    protected boolean tieneHijo;
    protected boolean estaCasado;
    protected int horasTrabajadas;

    public EmpleadoTemporario(boolean tieneHijo, boolean estaCasado, int horasTrabajadas){
        this.tieneHijo = tieneHijo;
        this.estaCasado = estaCasado;
        this.horasTrabajadas = horasTrabajadas;
    }

    @Override
    protected double calcularPorHora() {
        return 5.0 * this.horasTrabajadas;
    }

    @Override
    protected double sueldoBasico() {
        return 1000.0;
    }

    @Override
    protected double calcularPorHijoOCasado() {
        if (this.tieneHijo || this.estaCasado) {
            return 100.0;
        } else {
            return 0.0;
        }
    }
}


//El sueldo de un empleado Temporario está determinado por el pago
//de $5 por hora que trabajó más el sueldo básico que es de $1000, además se le paga $100
//si posee hijos y/o está casado.