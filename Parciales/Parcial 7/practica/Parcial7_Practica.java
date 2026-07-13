import java.time.LocalDate;
import java.util.*;



enum MedioDePago { DEBITO, CREDITO, EFECTIVO }

class Compra {
    private LocalDate fecha; private double montoOriginal, montoFinal; private MedioDePago medioDePago;
    public Compra(LocalDate fecha, double montoOriginal, MedioDePago medioDePago) {
        this.fecha=fecha; this.montoOriginal=montoOriginal; this.montoFinal=montoOriginal; this.medioDePago=medioDePago;
    }
    public LocalDate getFecha()      { return fecha; }
    public double getMontoOriginal() { return montoOriginal; }
    public double getMontoFinal()    { return montoFinal; }
    public void setMontoFinal(double m) { this.montoFinal = m; }
}

interface MailSender {                                    // INTERFAZ EXTERNA: usar, no implementar
    void enviarMail(String mailDestinatario, String titulo, String cuerpo);
}

class Cliente {                                           // Rol: CONTEXT (State)
    private String nombre, mail;
    private List<Compra> compras = new ArrayList<>();
    private Periodo periodo = new PeriodoBienvenida();    // fase inicial
    private MailSender mailSender;

    public Cliente(String nombre, String mail, MailSender mailSender) {
        this.nombre=nombre; this.mail=mail; this.mailSender=mailSender;
    }
    public void comprar(LocalDate fecha, double monto, MedioDePago medioDePago) {
        periodo.registrarCompra(this, new Compra(fecha, monto, medioDePago));   // DELEGA en el periodo
    }
    public void cambiarPeriodo(Periodo periodo) { this.periodo = periodo; }
    public Periodo getPeriodo()     { return periodo; }
    public boolean estaFidelizado() { return periodo.estaFidelizado(); }

    // --- servicios que usan los periodos ---
    public void agregarCompra(Compra compra) { compras.add(compra); }
    public Compra ultimaCompra() { return compras.isEmpty() ? null : compras.get(compras.size()-1); }
    public void enviarMail(String titulo, String cuerpo) { mailSender.enviarMail(mail, titulo, cuerpo); }

    // --- consultas que usan las estrategias de premio ---
    public int cantidadDeCompras()    { return compras.size(); }
    public double montoTotalGastado() { return compras.stream().mapToDouble(Compra::getMontoFinal).sum(); }
    public String getNombre()         { return nombre; }
}

// ================= STATE (periodos de fidelidad) =================
interface Periodo {                                       // Rol: STATE
    void registrarCompra(Cliente cliente, Compra compra);
    boolean estaFidelizado();
}

class PeriodoBienvenida implements Periodo {              // Rol: CONCRETE STATE
    public void registrarCompra(Cliente cliente, Compra compra) {
        compra.setMontoFinal(compra.getMontoOriginal() * 0.85);        // 15% off
        cliente.agregarCompra(compra);
        if (cliente.cantidadDeCompras() >= 10) {
            cliente.cambiarPeriodo(new PeriodoConsolidacion());        // TRANSICION
        }
    }
    public boolean estaFidelizado() { return false; }
}

class PeriodoConsolidacion implements Periodo {           // Rol: CONCRETE STATE
    private int cuponesRestantes = 3;                     // el contador es del ESTADO, no del cliente
    public void registrarCompra(Cliente cliente, Compra compra) {
        if (cuponesRestantes > 0 && compra.getMontoOriginal() > 50000) {
            compra.setMontoFinal(compra.getMontoOriginal() * 0.60);    // 40% off
            cuponesRestantes--;
        } else {
            compra.setMontoFinal(compra.getMontoOriginal());           // 100%
        }
        cliente.agregarCompra(compra);
        if (cuponesRestantes == 0) {
            cliente.cambiarPeriodo(new PeriodoFidelizado());           // TRANSICION
        }
    }
    public boolean estaFidelizado() { return false; }
}

class PeriodoFidelizado implements Periodo {              // Rol: CONCRETE STATE (final)
    public void registrarCompra(Cliente cliente, Compra compra) {
        boolean comproAyer = comproElDiaAnterior(cliente, compra);     // se mira ANTES de agregar
        compra.setMontoFinal(compra.getMontoOriginal());               // 100%, sin descuento
        cliente.agregarCompra(compra);
        if (comproAyer) {
            cliente.enviarMail("Gracias por elegirnos",
                "Gracias por permanecer con nosotros, es un orgullo tenerle como cliente");
        }
    }
    private boolean comproElDiaAnterior(Cliente cliente, Compra compra) {
        Compra ultima = cliente.ultimaCompra();
        return ultima != null && ultima.getFecha().equals(compra.getFecha().minusDays(1));
    }
    public boolean estaFidelizado() { return true; }
}

// ================= STRATEGY (criterio del premio) =================
interface EstrategiaPremio {                              // Rol: STRATEGY
    Cliente elegirGanador(List<Cliente> candidatos);
}
class PremioPorMonto implements EstrategiaPremio {        // Rol: CONCRETE STRATEGY
    public Cliente elegirGanador(List<Cliente> candidatos) {
        return candidatos.stream().max(Comparator.comparingDouble(Cliente::montoTotalGastado)).orElse(null);
    }
}
class PremioPorCantidadDeCompras implements EstrategiaPremio {    // Rol: CONCRETE STRATEGY
    public Cliente elegirGanador(List<Cliente> candidatos) {
        return candidatos.stream().max(Comparator.comparingInt(Cliente::cantidadDeCompras)).orElse(null);
    }
}

class Supermercado {                                      // Rol: CONTEXT (Strategy)
    private List<Cliente> clientes = new ArrayList<>();
    private EstrategiaPremio estrategiaPremio;            // tipada con la INTERFAZ
    public Supermercado(EstrategiaPremio estrategiaPremio) { this.estrategiaPremio = estrategiaPremio; }
    public void agregarCliente(Cliente cliente) { clientes.add(cliente); }
    public void cambiarEstrategiaPremio(EstrategiaPremio e) { this.estrategiaPremio = e; }   // dinamico
    public Cliente mejorClienteFidelizado() {
        return estrategiaPremio.elegirGanador(
            clientes.stream().filter(Cliente::estaFidelizado).toList());    // solo los fidelizados
    }
}

