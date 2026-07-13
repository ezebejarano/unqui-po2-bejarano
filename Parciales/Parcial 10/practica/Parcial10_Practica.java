import java.util.*;



// ================= EJ. 2 - TEMPLATE METHOD =================
class Documento {                                   // Rol: CONTEXT (Strategy)
    private String fechaCreacion, titulo, text, firma;
    private Formateador formateador = new FormateadorTextoSimple();      // por defecto

    public Documento(String fechaCreacion, String titulo, String text, String firma) {
        this.fechaCreacion=fechaCreacion; this.titulo=titulo; this.text=text; this.firma=firma;
    }
    public String getFechaCreacion() { return fechaCreacion; }
    public String getTitulo()        { return titulo; }
    public String getText()          { return text; }
    public String getFirma()         { return firma; }

    public void setFormateador(Formateador f) { this.formateador = f; }  // cambio en RUNTIME
    public String mostrar() { return formateador.darFormato(this); }     // DELEGA
}

abstract class Formateador {                        // Rol: ABSTRACT CLASS
    // TEMPLATE METHOD: la secuencia. FINAL para que ninguna subclase la rompa.
    public final String darFormato(Documento d) {
        return formatearFecha(d) + formatearTitulo(d) + formatearTexto(d) + formatearFirma(d);
    }
    // PASOS PRIMITIVOS: cada formato los resuelve a su manera
    protected abstract String formatearFecha(Documento d);
    protected abstract String formatearTitulo(Documento d);
    protected abstract String formatearTexto(Documento d);
    protected abstract String formatearFirma(Documento d);
}

class FormateadorLatex extends Formateador {        // Rol: CONCRETE CLASS
    protected String formatearFecha(Documento d)  { return "\\date{" + d.getFechaCreacion() + "}\n"; }
    protected String formatearTitulo(Documento d) { return "\\title{" + d.getTitulo() + "}\n"; }
    protected String formatearTexto(Documento d)  { return "\\begin{document}\n" + d.getText()
                                                        + "\n\\end{document}\n"; }
    protected String formatearFirma(Documento d)  { return "\\footer{" + d.getFirma() + "}"; }
}

class FormateadorHTML extends Formateador {         // Rol: CONCRETE CLASS
    protected String formatearFecha(Documento d)  { return ""; }         // HTML NO muestra la fecha
    protected String formatearTitulo(Documento d) { return "<title>" + d.getTitulo() + "</title>\n"; }
    protected String formatearTexto(Documento d)  { return "<body>\n" + d.getText() + "\n</body>\n"; }
    protected String formatearFirma(Documento d)  { return "<footer>" + d.getFirma() + "</footer>"; }
}

class FormateadorTextoSimple extends Formateador {  // Rol: CONCRETE CLASS
    protected String formatearFecha(Documento d)  { return "Fecha de Creacion: " + d.getFechaCreacion() + "\n"; }
    protected String formatearTitulo(Documento d) { return "Titulo: " + d.getTitulo() + "\n"; }
    protected String formatearTexto(Documento d)  { return "Texto: " + d.getText() + "\n"; }
    protected String formatearFirma(Documento d)  { return "Pie: " + d.getFirma(); }
}

// ================= EJ. 1 - ADAPTER =================
interface ApiRedSocial {                            // Rol: TARGET (lo que el cliente espera)
    List<String> seguidores(String userId);
    int posts(String userId);
}

class ApiFacebook {                                 // Rol: ADAPTEE (existente: NO se toca)
    public List<String> friends(String facebookId) { return new ArrayList<>(); }
    public List<String> status(String facebookId)  { return new ArrayList<>(); }
}

class ApiFacebookAdapter implements ApiRedSocial {  // Rol: ADAPTER
    private ApiFacebook apiFacebook;
    public ApiFacebookAdapter(ApiFacebook apiFacebook) { this.apiFacebook = apiFacebook; }

    public List<String> seguidores(String userId) {
        return apiFacebook.friends(userId);              // traduce NOMBRE y PARAMETRO
    }
    public int posts(String userId) {
        return apiFacebook.status(userId).size();        // traduce el RETORNO: List -> int
    }
}

class AppAnalisisRedesSociales {                    // CLIENT: trabaja SIEMPRE contra el target
    private ApiRedSocial api;                       // tipada con la INTERFAZ
    public AppAnalisisRedesSociales(ApiRedSocial api) { this.api = api; }
    public boolean esInfluencer(String userId) {
        return api.seguidores(userId).size() > 10000 && api.posts(userId) > 100;
    }
}

