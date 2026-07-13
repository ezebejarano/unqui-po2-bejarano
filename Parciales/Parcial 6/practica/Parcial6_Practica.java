import java.util.*;



// ================= STRATEGY (forma de pago de los materiales) =================
interface EstrategiaFormaPago {                           // Rol: STRATEGY
    double ajustarPrecio(double montoEnEfectivo);
}
class PagoEfectivo implements EstrategiaFormaPago {       // Rol: CONCRETE STRATEGY
    public double ajustarPrecio(double monto) { return monto; }
}
class PagoMercadoPago implements EstrategiaFormaPago {    // Rol: CONCRETE STRATEGY
    public double ajustarPrecio(double monto) { return monto * 0.97; }                 // -3%
}
class PagoTarjetaCredito implements EstrategiaFormaPago { // Rol: CONCRETE STRATEGY
    private int cuotas;
    public PagoTarjetaCredito(int cuotas) { this.cuotas = cuotas; }
    public double ajustarPrecio(double monto) { return monto * (1 + 0.04 * cuotas); }  // +4% x cuota
}

// ================= COMPOSITE (actividades) =================
interface Actividad {                                     // Rol: COMPONENT
    double costo(EstrategiaFormaPago formaDePago);        // la strategy VIAJA por el arbol
}

class Transporte implements Actividad {                   // Rol: LEAF
    private double distancia, peso, precio;
    public Transporte(double distancia, double peso, double precio) {
        this.distancia=distancia; this.peso=peso; this.precio=precio;
    }
    public double costo(EstrategiaFormaPago fp) { return distancia * peso * precio; }  // ignora fp
}

class Obra implements Actividad {                         // Rol: LEAF
    private List<Material> materiales = new ArrayList<>();
    private List<Operario> operarios  = new ArrayList<>();
    public void agregarMaterial(Material m) { materiales.add(m); }
    public void agregarOperario(Operario o) { operarios.add(o); }

    public double costo(EstrategiaFormaPago fp) {
        return fp.ajustarPrecio(costoMateriales()) + costoOperarios();  // la strategy solo toca materiales
    }
    private double costoMateriales() { return materiales.stream().mapToDouble(Material::subtotal).sum(); }
    private double costoOperarios()  { return operarios.stream().mapToDouble(Operario::costo).sum(); }
}

class Proyecto implements Actividad {                     // Rol: COMPOSITE
    private String nombre;
    private List<Actividad> actividades = new ArrayList<>();
    public Proyecto(String nombre) { this.nombre = nombre; }
    public void agregarActividad(Actividad a)  { actividades.add(a); }
    public void eliminarActividad(Actividad a) { actividades.remove(a); }

    public double costo(EstrategiaFormaPago fp) {
        double total = 0;
        for (Actividad actividad : actividades) { total += actividad.costo(fp); }  // recursivo
        return total * 1.20;                                                       // +20% de gestion
    }
}

// ================= DOMINIO =================
class Material {
    private int cantidad; private double precioUnitario;
    public Material(int cantidad, double precioUnitario) { this.cantidad=cantidad; this.precioUnitario=precioUnitario; }
    public double subtotal() { return cantidad * precioUnitario; }
}
class Operario {
    private double horas, valorHora; private int antiguedad;
    public Operario(double horas, double valorHora, int antiguedad) {
        this.horas=horas; this.valorHora=valorHora; this.antiguedad=antiguedad;
    }
    public double costo() {
        double base = horas * valorHora;
        return antiguedad > 5 ? base * 1.10 : base;       // +10% si antiguedad > 5
    }
}

// ================= CONTEXT =================
class EmpresaIngenieria {                                 // Rol: CONTEXT + cliente del Composite
    private String razonSocial, cuit;
    private Actividad actividadActual;                    // tipada con el COMPONENT
    private EstrategiaFormaPago formaDePago;              // tipada con la STRATEGY

    public EmpresaIngenieria(String razonSocial, String cuit, Actividad actividadActual,
                             EstrategiaFormaPago formaDePago) {
        this.razonSocial=razonSocial; this.cuit=cuit;
        this.actividadActual=actividadActual; this.formaDePago=formaDePago;
    }
    public void setFormaDePago(EstrategiaFormaPago fp) { this.formaDePago = fp; }  // cambio en runtime
    public double costoAAfrontar() { return actividadActual.costo(formaDePago); }
}

