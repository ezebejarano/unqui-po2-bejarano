package ar.edu.unq.po2.TPTemplateAdapter.Empleado;

public class EmpleadoPasante extends Empleado {
    protected int horasTrabajadas;

    public EmpleadoPasante(int horasTrabajadas){
        this.horasTrabajadas = horasTrabajadas;
    }
    @Override
    protected double calcularPorHora() {
        return 40.0 * this.horasTrabajadas;
    }

    @Override
    protected double sueldoBasico() {
        return 0;
    }

}

//A los Pasantes se les paga $40 las horas trabajadas en el mes.