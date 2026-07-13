package ar.edu.unq.po2.TPTemplateAdapter.Empleado;

public abstract class Empleado {
    public final double sueldo(){
        double sueldoBruto = this.sueldoBasico() + this.calcularPorHora() + this.calcularPorHijoOCasado();
        return sueldoBruto - (sueldoBruto * 0.13);
    }

    protected abstract double calcularPorHora();
    protected abstract double sueldoBasico();
    protected double calcularPorHijoOCasado() { return 0.0; } // Implementación por defecto (Hook) asi puedo no usarlo en EmpleadoPasante

    }


//Ejercicio 2 - Sueldos recargado
//Una empresa realiza periódicamente los pagos de sueldos y cargas de cada uno de sus
//empleados. En la empresa los empleados están organizados en tres tipos: Temporarios,
//Pasantes y de Planta.

//TEORIA: iNDICAR LOS ROLES PARTICIPANTES
//LA CLASE ABSTRACDA (EMPLEADO): Define la plantilla del algoritmo y crea un metodo final llamado sueldo
//que entienden todas las clases hijas y no pueden cambiar y declara los métodos abstractos que componen los pasos del
//sueldo bruto.

//LAS CLASES CONCRETAS: EmpleadoDePlanta, EmpleadoTemporario, EmpleadoPasante. Implementan los pasos especificos
//para calcular el basico, las horas y la familia segun lo que corresponda.