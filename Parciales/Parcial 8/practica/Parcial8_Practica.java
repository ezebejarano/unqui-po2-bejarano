import java.time.LocalDate;
import java.util.*;



enum WeatherEvent { NIEBLA, LLUVIAS_FUERTES }

// ================= EJ. 1 - OBSERVER =================
interface AirportStatus {                            // Rol: OBSERVER (y TARGET del adapter)
    void airportStatusOK();
    void airportClosedWeahter(WeatherEvent reason);
    void airportClosed(String reason);
    void departFlight(String flightNumber, LocalDate time);
    void arriveFlight(String flightNumber, LocalDate time);
}

class Aeropuerto {                                   // Rol: SUBJECT
    private String nombre;
    private List<AirportStatus> observadoresDelEstado = new ArrayList<>();          // condiciones del aero
    private Map<String, List<AirportStatus>> observadoresPorVuelo = new HashMap<>(); // "AR1234" -> [..]
    private List<AirportStatus> observadoresDeTodosLosVuelos = new ArrayList<>();    // los que no eligieron

    public Aeropuerto(String nombre) { this.nombre = nombre; }

    // --- registracion ---
    public void registrar(AirportStatus o) {                                 // "a los eventos en general"
        registrarEnEstado(o);
        if (!observadoresDeTodosLosVuelos.contains(o)) { observadoresDeTodosLosVuelos.add(o); }
    }
    public void registrarEnEstado(AirportStatus o) {                         // solo condiciones
        if (!observadoresDelEstado.contains(o)) { observadoresDelEstado.add(o); }
    }
    public void registrarEnVuelo(AirportStatus o, String flightNumber) {     // solo ese vuelo
        observadoresPorVuelo.computeIfAbsent(flightNumber, v -> new ArrayList<>()).add(o);
    }
    public void desregistrar(AirportStatus o) {
        observadoresDelEstado.remove(o);
        observadoresDeTodosLosVuelos.remove(o);
        observadoresPorVuelo.values().forEach(lista -> lista.remove(o));
    }
    public void desregistrarDeVuelo(AirportStatus o, String flightNumber) {
        observadoresPorVuelo.getOrDefault(flightNumber, new ArrayList<>()).remove(o);
    }

    // --- eventos: cada uno notifica SOLO a los interesados ---
    public void estaOperativo() {
        observadoresDelEstado.forEach(AirportStatus::airportStatusOK);
    }
    public void cerrarPorClima(WeatherEvent razon) {
        observadoresDelEstado.forEach(o -> o.airportClosedWeahter(razon));
    }
    public void cerrarPorOtraRazon(String razon) {                     // energia, paros, etc.
        observadoresDelEstado.forEach(o -> o.airportClosed(razon));
    }
    public void despegar(String flightNumber, LocalDate time) {
        interesadosEn(flightNumber).forEach(o -> o.departFlight(flightNumber, time));
    }
    public void aterrizar(String flightNumber, LocalDate time) {
        interesadosEn(flightNumber).forEach(o -> o.arriveFlight(flightNumber, time));
    }
    // los de ESE vuelo + los de todos los vuelos (Set: no notificar dos veces al que esta en ambas)
    private Set<AirportStatus> interesadosEn(String flightNumber) {
        Set<AirportStatus> interesados = new HashSet<>(observadoresDeTodosLosVuelos);
        interesados.addAll(observadoresPorVuelo.getOrDefault(flightNumber, new ArrayList<>()));
        return interesados;
    }
}

class MedioDeComunicacion implements AirportStatus {    // Rol: CONCRETE OBSERVER
    private String nombre;
    public MedioDeComunicacion(String nombre) { this.nombre = nombre; }
    public void airportStatusOK()                    { print("aeropuerto operativo"); }
    public void airportClosedWeahter(WeatherEvent r) { print("cerrado por clima (" + r + ")"); }
    public void airportClosed(String r)              { print("cerrado (" + r + ")"); }
    public void departFlight(String n, LocalDate t)  { print("despego " + n + " el " + t); }
    public void arriveFlight(String n, LocalDate t)  { print("aterrizo " + n + " el " + t); }
    private void print(String msg) { System.out.println(nombre + ": " + msg); }
}

// ================= EJ. 2 - ADAPTER =================
interface Aeropuertos {                              // Rol: ADAPTEE (la interfaz de la OTRA empresa)
    void estadoOK();
    void aeroCerradoPorClima(WeatherEvent reason);
    void aeropuertoCerrado(String reason);
    void vueloDespegando(String flightNumber, LocalDate time);
    void vueloAterrizando(String flightNumber, LocalDate time);
}

class AppMovilDeLaEmpresa implements Aeropuertos {   // clase ya existente: NO se toca
    public void estadoOK()                              { System.out.println("[APP] Todo OK"); }
    public void aeroCerradoPorClima(WeatherEvent r)     { System.out.println("[APP] Clima: " + r); }
    public void aeropuertoCerrado(String r)             { System.out.println("[APP] Cerrado: " + r); }
    public void vueloDespegando(String n, LocalDate t)  { System.out.println("[APP] Despega " + n); }
    public void vueloAterrizando(String n, LocalDate t) { System.out.println("[APP] Aterriza " + n); }
}

class AeropuertosAdapter implements AirportStatus {  // Rol: ADAPTER (implementa el TARGET...)
    private Aeropuertos adaptado;                    // ...y delega en el ADAPTEE traduciendo
    public AeropuertosAdapter(Aeropuertos adaptado) { this.adaptado = adaptado; }

    public void airportStatusOK()                    { adaptado.estadoOK(); }
    public void airportClosedWeahter(WeatherEvent r) { adaptado.aeroCerradoPorClima(r); }
    public void airportClosed(String r)              { adaptado.aeropuertoCerrado(r); }
    public void departFlight(String n, LocalDate t)  { adaptado.vueloDespegando(n, t); }
    public void arriveFlight(String n, LocalDate t)  { adaptado.vueloAterrizando(n, t); }
}

