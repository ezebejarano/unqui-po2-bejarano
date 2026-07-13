import java.util.*;



enum Severidad { TRACE, DEBUG, INFO, WARN, ERROR, FATAL }   // el ORDEN de declaracion ES el orden

// ================= INTERFACES EXTERNAS (usar, no implementar) =================
interface IMonitor {
    void printScreen(String msg, String color);
    String BLUE = "Blue"; String YELLOW = "Yellow"; String RED = "Red"; String GRAY = "Gray";
}
interface IEmailClient { void sendEmail(String to, String subject, String text); }

// ================= OBSERVER =================
class Logger {                                       // Rol: SUBJECT
    private List<SistemaDeEscucha> sistemas = new ArrayList<>();

    public void registrar(SistemaDeEscucha s)    { sistemas.add(s); }
    public void desregistrar(SistemaDeEscucha s) { sistemas.remove(s); }

    public void log(String mensaje, Severidad severidad) {          // (1) recibir -> notificar a todos
        for (SistemaDeEscucha sistema : sistemas) {
            sistema.recibirMensaje(mensaje, severidad);
        }
    }
}

class SistemaDeEscucha {                             // Rol: CONCRETE OBSERVER + CONTEXT (Strategy)
    private IMonitor monitor;                        // su UNICO monitor
    private EstrategiaRegla regla;                   // tipada con la INTERFAZ

    public SistemaDeEscucha(IMonitor monitor, EstrategiaRegla regla) {
        this.monitor = monitor; this.regla = regla;
    }
    public void cambiarRegla(EstrategiaRegla regla) { this.regla = regla; }   // cambio en RUNTIME

    public void recibirMensaje(String mensaje, Severidad severidad) {
        regla.publicar(mensaje, severidad, this);    // DELEGA en su regla
    }
    public IMonitor getMonitor() { return monitor; }
}

// ================= STRATEGY (reglas de publicacion) =================
interface EstrategiaRegla {                          // Rol: STRATEGY
    void publicar(String mensaje, Severidad severidad, SistemaDeEscucha sistema);
}

class ReglaVerbose implements EstrategiaRegla {      // Rol: CONCRETE STRATEGY
    private List<String> mensajesEnviados = new ArrayList<>();
    private IEmailClient emailClient; private String destinatario;

    public ReglaVerbose(IEmailClient emailClient, String destinatario) {
        this.emailClient = emailClient; this.destinatario = destinatario;
    }
    public void publicar(String mensaje, Severidad severidad, SistemaDeEscucha sistema) {
        sistema.getMonitor().printScreen(mensaje, IMonitor.GRAY);       // TODOS, en gris
        mensajesEnviados.add(mensaje);
        if (mensajesEnviados.size() == 100) {                           // a los 100 -> mail
            emailClient.sendEmail(destinatario, "Resumen del logger", String.join("\n", mensajesEnviados));
            mensajesEnviados.clear();
        }
    }
}

class ReglaProduccion implements EstrategiaRegla {   // Rol: CONCRETE STRATEGY
    private Set<Severidad> niveles;                  // SU configuracion (por instancia)

    public ReglaProduccion(Severidad... niveles) { this.niveles = new HashSet<>(Arrays.asList(niveles)); }

    public void publicar(String mensaje, Severidad severidad, SistemaDeEscucha sistema) {
        if (niveles.contains(severidad)) {                              // si no esta: no publica nada
            sistema.getMonitor().printScreen(mensaje, colorPara(severidad));
        }
    }
    private String colorPara(Severidad s) {
        if (s == Severidad.FATAL || s == Severidad.ERROR) { return IMonitor.RED; }
        if (s == Severidad.WARN)                          { return IMonitor.YELLOW; }
        return IMonitor.BLUE;
    }
}

