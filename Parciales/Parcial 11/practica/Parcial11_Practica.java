import java.time.LocalDate;
import java.util.*;



interface Empleado {
    String nombre();
    String rol();
    double sueldoDiario();
}

class EmpleadoPublico implements Empleado {
    private String nombre, rol; private double sueldoDiario;
    public EmpleadoPublico(String nombre, String rol, double sueldoDiario) {
        this.nombre=nombre; this.rol=rol; this.sueldoDiario=sueldoDiario;
    }
    public String nombre()       { return nombre; }
    public String rol()          { return rol; }
    public double sueldoDiario() { return sueldoDiario; }
}

// ================= COMPOSITE (obras) =================
abstract class Obra {                                // Rol: COMPONENT (abstracta: tiene ESTADO)
    private String nombre;
    private LocalDate fechaDeInicio;
    private Empleado lider;

    public Obra(String nombre, LocalDate fechaDeInicio, Empleado lider) {
        this.nombre=nombre; this.fechaDeInicio=fechaDeInicio; this.lider=lider;
    }
    public String getNombre()  { return nombre; }
    public Empleado getLider() { return lider; }
    public LocalDate getFechaDeInicio() { return fechaDeInicio; }

    public abstract double inversionTotal();
}

class Proyecto extends Obra {                        // Rol: LEAF
    private int duracionEnDias;
    private List<Empleado> empleados;

    public Proyecto(String nombre, LocalDate fechaDeInicio, Empleado lider,
                    List<Empleado> empleados, int duracionEnDias) {
        super(nombre, fechaDeInicio, lider);
        this.empleados = new ArrayList<>(empleados);
        this.duracionEnDias = duracionEnDias;
    }
    public double inversionTotal() { return sueldoDeLosEmpleados() + sueldoDelLider(); }

    private double sueldoDeLosEmpleados() {
        return empleados.stream().mapToDouble(Empleado::sueldoDiario).sum() * duracionEnDias;
    }
    private double sueldoDelLider() {
        return getLider().sueldoDiario() * duracionEnDias * 1.30;      // el lider cobra 30% extra
    }
}

class Programa extends Obra {                        // Rol: COMPOSITE
    private List<Obra> obras = new ArrayList<>();
    private double costoFijoDeGestion;

    public Programa(String nombre, LocalDate fechaDeInicio, Empleado lider, double costoFijoDeGestion) {
        super(nombre, fechaDeInicio, lider);
        this.costoFijoDeGestion = costoFijoDeGestion;
    }
    public void agregarObra(Obra obra)  { obras.add(obra); }
    public void eliminarObra(Obra obra) { obras.remove(obra); }

    public double inversionTotal() {                                   // recursivo (puede haber programas)
        return costoFijoDeGestion + obras.stream().mapToDouble(Obra::inversionTotal).sum();
    }
    @Override
    public LocalDate getFechaDeInicio() {                             // la MAS TEMPRANA de sus obras
        return obras.stream().map(Obra::getFechaDeInicio)
                    .min(Comparator.naturalOrder())
                    .orElse(super.getFechaDeInicio());
    }
}

// ================= ADAPTER (secretarias) =================
interface Secretaria {                               // Rol: TARGET
    double montoTotal();
}

class SecretariaDeObrasPublicas implements Secretaria {     // ya habla el protocolo del target
    private String nombre;
    private List<Obra> obras = new ArrayList<>();
    public SecretariaDeObrasPublicas(String nombre) { this.nombre = nombre; }
    public void agregarObra(Obra obra) { obras.add(obra); }
    public double montoTotal() { return obras.stream().mapToDouble(Obra::inversionTotal).sum(); }
}

class SecretariaDeInfraestructura {                  // Rol: ADAPTEE (otro protocolo: NO se toca)
    private List<Obra> obras = new ArrayList<>();
    public void agregarObra(Obra obra) { obras.add(obra); }
    public double inversionTotal() { return obras.stream().mapToDouble(Obra::inversionTotal).sum(); }
}

class SecretariaDeInfraestructuraAdapter implements Secretaria {   // Rol: ADAPTER
    private SecretariaDeInfraestructura secretaria;
    public SecretariaDeInfraestructuraAdapter(SecretariaDeInfraestructura s) { this.secretaria = s; }
    public double montoTotal() { return secretaria.inversionTotal(); }   // traduce la llamada
}

class MinisterioDePlanificacion {                    // Rol: CLIENT (solo conoce la INTERFAZ)
    private List<Secretaria> secretarias = new ArrayList<>();
    public void agregarSecretaria(Secretaria s) { secretarias.add(s); }
    public double montoTotalAInvertir() {
        return secretarias.stream().mapToDouble(Secretaria::montoTotal).sum();
    }
}

