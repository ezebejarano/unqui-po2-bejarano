package ar.edu.unq.po2.TPTemplateAdapter.Empleado;

public class EmpleadoDePlanta extends Empleado {
    protected int cantidadDeHijos;

    public EmpleadoDePlanta(int cantidadDeHijos){
        this.cantidadDeHijos = cantidadDeHijos;
    }
    @Override
    protected double calcularPorHora() {
        return 0;
    }

    @Override
    protected double sueldoBasico() {
        return 3000.0;
    }

    @Override
    protected double calcularPorHijoOCasado() {
        return 150.0 * this.cantidadDeHijos;
    }
}

//Por último a los empleados de Planta se les paga un sueldo básico de $ 3000 y un
//plus por cada hijo que posea de $ 150 cada hijo. Por otro lado, de cada sueldo se deben
//descontar en conceptos de aportes y obra social un 13 % del sueldo.