package ar.edu.unq.po2.tp2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoPlantaTemporaria extends Empleado{
    private LocalDate fechaDesignacion;
    private int cantidadHorasExtra;

    public EmpleadoPlantaTemporaria(String nombre, String direccion, LocalDate fechaNacimiento, Double sueldoBasico, int cantidadHorasExtra) {
        super(nombre, direccion, fechaNacimiento, sueldoBasico);
        this.cantidadHorasExtra = cantidadHorasExtra;
        this.fechaDesignacion = fechaDesignacion;
    }

    @Override
    public Double sueldoBruto() {
        return this.getSueldoBasico() + (this.cantidadHorasExtra * 40);
    }

    @Override
    public Double retenciones() {
        return this.aporteObraSocial() + this.aporteJubilatorio();
    }

    @Override
    public List<String> getDesgloce() {
        List<String> items = new ArrayList<>();
        items.add("Sueldo Básico: " + this.getSueldoBasico());
        items.add("Horas Extras: " + (this.cantidadHorasExtra * 40));
        items.add("Retención Obra Social: " + this.aporteObraSocial());
        items.add("Retención Jubilación: " + this.aporteJubilatorio());
        return items;
    }

    private Double aporteObraSocial(){
        Double montoPorEdad = (this.edad() > 50) ? 25.0 : 0.0;
        return (this.sueldoBruto() * 0.10) + montoPorEdad;
    }

    private Double aporteJubilatorio(){
        return (this.sueldoBruto() * 0.10) + (this.cantidadHorasExtra * 5);
    }
}


//Los empleados de planta temporaria tienen, además de la información básica, la siguiente:
//Fecha de fin de designación a planta temporaria Cantidad de Horas Extras