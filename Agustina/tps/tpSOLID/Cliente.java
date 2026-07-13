package ar.edu.unq.po2.tpSOLID;

public class Cliente {
    private String nombre;
    private String apellido;
    private String direccion;
    private int edad;
    private Double sueldoNetoMensual;

    public Cliente(String nombre, String apellido, String direccion, int edad, Double sueldoNetoMensual){
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.edad = edad;
        this.sueldoNetoMensual = sueldoNetoMensual;
    }

    public Double sueldoNetoAnual(){
        return this.sueldoNetoMensual * 12;
    }

    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    public int getEdad(){
        return this.edad;
    }

    public Double getSueldoNetoMensual(){
        return this.sueldoNetoMensual;
    }
}
