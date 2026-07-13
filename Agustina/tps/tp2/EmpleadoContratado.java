package ar.edu.unq.po2.tp2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoContratado extends Empleado{
    private String estadoCivil;
    private int numeroDeContrato;
    private String medioDePago;

    public EmpleadoContratado(String nombre, String direccion, LocalDate fechaNacimiento, Double sueldoBasico, String estadoCivil, int numeroDeContrato, String medioDePago) {
        super(nombre, direccion, fechaNacimiento, sueldoBasico);
        this.estadoCivil = estadoCivil;
        this.numeroDeContrato = numeroDeContrato;
        this.medioDePago = medioDePago;
    }

    @Override
    public Double sueldoBruto() {
        return this.sueldoBasico;
    }

    @Override
    public Double retenciones() {
        return 50.0;
    }

    @Override
    public List<String> getDesgloce() {
        List<String> items = new ArrayList<>();
        items.add("Sueldo Básico (Bruto): " + this.sueldoBruto());
        items.add("Gastos Administrativos Contractuales: $50.0");
        items.add("Medio de Pago: " + this.medioDePago);
        return items;
    }
}
