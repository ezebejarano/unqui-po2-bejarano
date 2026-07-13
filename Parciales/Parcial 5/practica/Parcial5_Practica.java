import java.util.*;



class Item {                                   // dominio
    private int cantidad; private double valorUnitario;
    public Item(int cantidad, double valorUnitario) { this.cantidad=cantidad; this.valorUnitario=valorUnitario; }
    public double valor() { return cantidad * valorUnitario; }        // 4 sillas x $2000 = 8000
}

class GastoAdministrativo {                    // dominio (el monto puede ser negativo)
    private String concepto; private double monto;
    public GastoAdministrativo(String concepto, double monto) { this.concepto=concepto; this.monto=monto; }
    public double getMonto() { return monto; }
}

interface BonificacionService {                // INTERFAZ EXTERNA: usar, no implementar
    boolean codigoValido(int codigo);
    void anularCodigo(int codigo);
    void notificarTitular(String mailTitular, int codigo);
}

class Poliza {                                 // Rol: CONTEXT
    private String mailTitular;
    private List<Item> inventario = new ArrayList<>();
    private List<GastoAdministrativo> gastos = new ArrayList<>();
    private EstadoPoliza estado = new Abierta();          // fase inicial
    private BonificacionService bonificacionService;      // tipada con la INTERFAZ

    public Poliza(String mailTitular, BonificacionService bonificacionService) {
        this.mailTitular = mailTitular; this.bonificacionService = bonificacionService;
    }

    // --- delega en el estado ---
    public Poliza agregarItem(Item item) { return estado.agregarItem(this, item); }  // encadenable
    public void cerrarInventario()       { estado.cerrarInventario(this); }
    public void pagar()                  { estado.pagar(this); }
    public void cancelar()               { estado.cancelar(this); }

    // --- el algoritmo de bonificacion vive ACA (no es Template Method) ---
    public void bonificar(int codigo) {
        if (bonificacionService.codigoValido(codigo)) {                 // 1
            estado.aplicarDescuento(this);                              // 2 <- DEPENDE DE LA FASE
            bonificacionService.anularCodigo(codigo);                   // 3
            bonificacionService.notificarTitular(mailTitular, codigo);  // 4
        }
    }

    // --- consultas ---
    public double montoAsegurado() { return inventario.stream().mapToDouble(Item::valor).sum(); }
    public double precio()         { return montoAsegurado() * 0.0075 + totalGastos(); }   // 0,75% !!
    public double totalGastos()    { return gastos.stream().mapToDouble(GastoAdministrativo::getMonto).sum(); }

    // --- servicios que usan los estados ---
    public void setEstado(EstadoPoliza estado) { this.estado = estado; }
    public EstadoPoliza getEstado()            { return estado; }
    public void agregarAlInventario(Item item) { inventario.add(item); }
    public void agregarGasto(GastoAdministrativo gasto) { gastos.add(gasto); }
    public void eliminarGastoDeMayorValor() {
        gastos.stream().max(Comparator.comparingDouble(GastoAdministrativo::getMonto))
              .ifPresent(gastos::remove);
    }
    public void eliminarTodosLosGastos() { gastos.clear(); }
    public List<GastoAdministrativo> getGastos() { return gastos; }
    public List<Item> getInventario()            { return inventario; }
}

abstract class EstadoPoliza {                  // Rol: STATE. Vacios por defecto = "no surten efecto"
    public Poliza agregarItem(Poliza p, Item i) { return p; }
    public void cerrarInventario(Poliza p) { }
    public void pagar(Poliza p) { }
    public void cancelar(Poliza p) { }
    public abstract void aplicarDescuento(Poliza p);      // el paso 2: SI depende de la fase
}

class Abierta extends EstadoPoliza {           // Rol: CONCRETE STATE
    public Poliza agregarItem(Poliza p, Item i) { p.agregarAlInventario(i); return p; }
    public void cerrarInventario(Poliza p)      { p.setEstado(new Cerrada()); }        // TRANSICION
    public void aplicarDescuento(Poliza p) {
        p.agregarGasto(new GastoAdministrativo("Bonificacion Administrativa", -500));
    }
}

class Cerrada extends EstadoPoliza {           // Rol: CONCRETE STATE
    public Poliza agregarItem(Poliza p, Item i) {
        p.agregarAlInventario(i);
        p.agregarGasto(new GastoAdministrativo("Recargo por extension", i.valor() * 0.03));   // 3%
        return p;
    }
    public void pagar(Poliza p)            { p.setEstado(new Vigente()); }             // TRANSICION
    public void aplicarDescuento(Poliza p) { p.eliminarGastoDeMayorValor(); }
}

class Vigente extends EstadoPoliza {           // Rol: CONCRETE STATE (agregarItem: hereda el vacio)
    public void cancelar(Poliza p) { p.eliminarTodosLosGastos(); p.setEstado(new Abierta()); }  // TRANSICION
    public void aplicarDescuento(Poliza p) { p.eliminarTodosLosGastos(); }
}

