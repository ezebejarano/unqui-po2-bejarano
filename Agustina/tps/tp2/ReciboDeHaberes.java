package ar.edu.unq.po2.tp2;

import java.time.LocalDate;
import java.util.List;

public class ReciboDeHaberes {
    private String nombre;
    private String direccion;
    private LocalDate fechaEmision;
    private Double sueldoBruto;
    private Double sueldoNeto;
    private String desgloce;

    public ReciboDeHaberes(Empleado e){
        this.nombre = e.getNombre();
        this.direccion = e.getDireccion();
        this.fechaEmision = LocalDate.now();
        this.sueldoBruto = e.sueldoBruto();
        this.sueldoNeto = e.sueldoNeto();
        this.desgloce = "Conceptos: " + e.getDesgloce();
    }
}
