package ar.edu.unq.po2.tp2;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private String nombre;
    private int cuit;
    private List<Empleado> empleados;
    private List<ReciboDeHaberes> recibos;

    public Empresa(String nombre, int cuit){
        this.nombre = nombre;
        this.cuit = cuit;
        this.empleados = new ArrayList<>();
        this.recibos = new ArrayList<>();
    }

    public void liquidarSueldos(){
        for (Empleado e : empleados) {
            ReciboDeHaberes recibo = new ReciboDeHaberes(e);
            this.recibos.add(recibo);
        }
    }

    public void agregarEmpleado(Empleado empleado){
        this.empleados.add(empleado);
    }

    public Double montoTotalSueldosNetos(){
        Double total = 0d;
        for (Empleado empleado : empleados){
            total += empleado.sueldoNeto();
        }
        return total;
    }

    public Double montoTotalSueldosBrutos(){
        Double total = 0d;
        for (Empleado empleado : empleados){
            total += empleado.sueldoBruto();
        }
        return total;
    }

    public Double montoTotalRetenciones(){
        Double total = 0d;
        for (Empleado empleado : empleados){
            total += empleado.retenciones();
        }
        return total;
    }

    public List<ReciboDeHaberes> getRecibos() {
        return recibos;
    }
}




//Se desea modelar una empresa y los empleados que ésta gestiona según las pautas que se describen a continuación.
//La empresa tiene un nombre, un CUIT y un conjunto de empleados, cada uno de los cuales tiene la siguiente información básica:
