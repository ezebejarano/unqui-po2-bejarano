package ar.edu.unq.po2.tp2;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public abstract class Empleado {
    private String nombre;
    private String direccion;
    private LocalDate fechaNacimiento;
    protected Double sueldoBasico;

    public Empleado(String nombre, String direccion, LocalDate fechaNacimiento, Double sueldoBasico){
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.sueldoBasico = sueldoBasico;
    }

    public Double sueldoNeto(){
        return this.sueldoBruto() - this.retenciones();
    }

    public abstract Double sueldoBruto();
    public abstract Double retenciones();

    public int edad(){
        LocalDate fechaActual= LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, fechaActual);
        return periodo.getYears();
    }

    public String getNombre() { return nombre;}
    public String getDireccion(){ return direccion;}
    public Double getSueldoBasico(){ return sueldoBasico;}

    public abstract List<String> getDesgloce();
}

//No obstante la clasificación planteada anteriormente, es política de la empresa manejar a todos los empleados como un conjunto homogéneo sin hacer diferencias entre ambos tipos.
//Cada empleado puede calcular su sueldo bruto, las retenciones que se le aplican y el sueldo neto resultante (que se obtiene al restar las dos primeras cantidades).
// La composición de del sueldo bruto y
//de las retenciones dependen del tipo de empleado, como se explica a continuación. El sueldo bruto del empleado de planta permanente se compone de:
//Sueldo Básico
// Salario Familiar, que se compone de:
//Asignación por hijo: $150 por cada hijo.
//Asignación por cónyuge: $100 si tiene cónyuge Antigüedad: $50 por cada año de antigüedad.
//Las retenciones que se realizan a este empleado son:
//Obra Social: 10% de su sueldo bruto + $20 por cada hijo. Aportes Jubilatorios: 15% de su sueldo bruto
//Por otro lado, el sueldo bruto del empleado de planta temporaria se compone de:
//Sueldo Básico
//Horas Extras: $40 por cada hora extra
//Las retenciones que se realizan a este empleado son:
//Obra Social: 10% de su sueldo bruto + $25 si supera los 50 años Aportes Jubilatorios: 10% de su sueldo bruto + $5 por cada hora extra.

//Nombre
//Dirección
//Estado Civil
//Fecha de Nacimiento Sueldo Básico

//Además, cada empleado puede calcular su edad a partir de su fecha de nacimiento.
//Los empleados se dividen en dos clasificaciones: pueden ser de planta permanente o temporaria.


