package ar.edu.unq.po2.tp2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoPlantaPermanente extends Empleado{
    private int cantidadHijos;
    private int antiguedad;
    private Boolean tieneConyuge;


    public EmpleadoPlantaPermanente(String nombre, String direccion, LocalDate fechaNacimiento, Double sueldoBasico, int hijos, boolean conyuge, int antiguedad) {
        super(nombre, direccion, fechaNacimiento, sueldoBasico);
        this.cantidadHijos = hijos;
        this.tieneConyuge = conyuge;
        this.antiguedad = antiguedad;
    }

    @Override
    public Double sueldoBruto() {
        return this.getSueldoBasico() + this.asignacionPorHijo() + this.asignacionPorConyuge() + this.montoPorAntiguedad();
    }

    public Double asignacionPorHijo(){ return (double) (this.cantidadHijos * 150); }
    public Double asignacionPorConyuge(){ return tieneConyuge ? 100.0 : 0.0; }
    public Double montoPorAntiguedad(){ return (double) (this.antiguedad * 50);}

    @Override
    public Double retenciones() {
        return this.obraSocial() + this.aportesJubilatorios();
    }

    @Override
    public List<String> getDesgloce() {
        List<String> items = new ArrayList<>();
        items.add("Sueldo Básico: " + this.getSueldoBasico());
        items.add("Asignación por Hijo: " + this.asignacionPorHijo());
        items.add("Asignación por Cónyuge: " + this.asignacionPorConyuge());
        items.add("Antigüedad: " + this.montoPorAntiguedad());
        items.add("Retención Obra Social: " + this.obraSocial());
        items.add("Retención Jubilación: " + this.aportesJubilatorios());
        return items;
    }

    public Double obraSocial(){ return (this.sueldoBruto() * 0.10) + (this.cantidadHijos * 20);}
    public Double aportesJubilatorios(){ return this.sueldoBruto() * 0.15; }

}


//Los empleados de planta permanente tienen, además de la información básica, la siguiente:
//Cantidad de Hijos Antigüedad