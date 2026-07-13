// =============================================================================
//  PARCIAL 3 - PRACTICA  (Sistema de seguimiento de pedidos)
//  Escribi TODA tu solucion en ESTE archivo, como si fuera la hoja del parcial.
//  Cuando quieras que lo verifique, guarda y decime "verifica".
//
//  REGLA para que compile todo junto en un archivo:
//   - No pongas "public" en las clases/interfaces (asi conviven todas aca).
//   - Para el ejemplo, hace una clase (sin public) con: public static void main(String[] args){...}
// =============================================================================
//
//  ------------------------------ EJERCICIO 1 (State) -------------------------
//  Un Pedido conoce un id y un cliente, y SIEMPRE esta en un estado.
//  Operaciones del pedido: confirmar(), enviar(), entregar(), cancelar().
//  El resultado de cada operacion DEPENDE del estado actual:
//   - Pendiente : confirmar -> Confirmado ; cancelar -> Cancelado. (resto invalido)
//   - Confirmado: enviar    -> Enviado    ; cancelar -> Cancelado. (resto invalido)
//   - Enviado   : entregar  -> Entregado. (ya NO se puede cancelar/confirmar/enviar)
//   - Entregado : final, nada permitido.
//   - Cancelado : final, nada permitido.
//  Operacion no permitida => lanzar excepcion:
//       throw new RuntimeException("mensaje claro");
//  Debe poder AGREGARSE un estado nuevo sin tocar los estados existentes.
//
//  ------------------------------ EJERCICIO 2 (Observer) ----------------------
//  Cada vez que el pedido CAMBIA de estado, avisa a todos los observadores
//  suscriptos. Interfaz externa (USAR, NO implementar):
//       public void seActualizo(Pedido pedido);
//  El pedido debe permitir suscribir y desuscribir observadores.
//
//  ------------------------------- ACTIVIDADES --------------------------------
//   1) UML integrado de ambos ejercicios.
//   2) Patrones usados + roles segun Gamma et al.
//   3) Implementar en Java con constructores. Solo los accessors que necesites.
//   4) Ejemplo: crear un pedido, suscribir un observador, recorrer
//      Pendiente -> Confirmado -> Enviado -> Entregado mostrando los avisos,
//      y probar una operacion invalida para ver que lanza la excepcion.
//
//  Pista (checklist de clases, tapala si queres practicar sin ayuda):
//    [ ] EstadoPedido (abstracta o interface: confirmar/enviar/entregar/cancelar)
//    [ ] Pendiente / Confirmado / Enviado / Entregado / Cancelado
//    [ ] Pedido (el Context del State y el Subject del Observer)
//    [ ] ObservadorDePedido (interface, NO implementar) con seActualizo(Pedido)
//    [ ] clase con main() para el ejemplo
// =============================================================================

import java.util.ArrayList;
import java.util.List;

// ============================================================================
//  ESCRIBI TU SOLUCION DE ACA PARA ABAJO
// ============================================================================


// ===================== EJERCICIO 1 - PATRON STATE ==========================

// Rol: STATE (la interfaz/tipo comun de todos los estados).
// Define QUE operaciones existen (confirmar/enviar/entregar/cancelar), y por
// defecto todas lanzan excepcion ("no permitido"). Truco clave: cada estado
// concreto solo REESCRIBE las transiciones que SI puede hacer; las que no toca
// quedan prohibidas automaticamente (heredan el throw de aca). Asi no repetimos
// el error en cada estado y sumar un estado nuevo es solo crear una subclase.
abstract class EstadoPedido {
    public void confirmar(Pedido pedido){
        throw new RuntimeException("Operacion no permitida en el estado actual.");
    }
    public void enviar(Pedido pedido){
        throw new RuntimeException("Operacion no permitida en el estado actual.");
    }
    public void entregar(Pedido pedido){
        throw new RuntimeException("Operacion no permitida en el estado actual.");
    }
    public void cancelar(Pedido pedido){
        throw new RuntimeException("Operacion no permitida en el estado actual.");
    }
    public abstract String getNombreEstado();
}

// Rol: CONCRETE STATE. Estado inicial. Solo sabe confirmar (-> Confirmado) y
// cancelar (-> Cancelado). Enviar y entregar los deja heredados => lanzan error.
class Pendiente extends EstadoPedido {
    public void confirmar (Pedido pedido){
        pedido.setEstado (new Confirmado());   // el estado se cambia a si mismo dentro del pedido
    }
    public void cancelar (Pedido pedido){
        pedido.setEstado (new Cancelado());
    }
    public String getNombreEstado() {
        return "Pendiente";
    }
}

// Rol: CONCRETE STATE. El pago fue aceptado. Sabe enviar (-> Enviado) y
// cancelar (-> Cancelado). Confirmar y entregar quedan prohibidos (heredados).
class Confirmado extends EstadoPedido {
    public void enviar (Pedido pedido){
        pedido.setEstado (new Enviado());
    }
    public void cancelar (Pedido pedido){
        pedido.setEstado (new Cancelado());
    }
    public String getNombreEstado() {
        return "Confirmado";
    }
}

// Rol: CONCRETE STATE. Ya salio del deposito. Solo sabe entregar (-> Entregado).
// Ya NO se puede cancelar/confirmar/enviar (quedan heredados => lanzan error).
class Enviado extends EstadoPedido {
    public void entregar (Pedido pedido){
        pedido.setEstado (new Entregado());
    }
    public String getNombreEstado() {
        return "Enviado";
    }
}

// Rol: CONCRETE STATE. Estado FINAL: no reescribe ninguna operacion, asi que
// todas quedan heredadas => cualquier cosa que le pidas lanza excepcion. Gratis.
class Entregado extends EstadoPedido {
    public String getNombreEstado() {
        return "Entregado";
    }
}

// Rol: CONCRETE STATE. Estado FINAL igual que Entregado: nada permitido.
class Cancelado extends EstadoPedido {
    public String getNombreEstado(){
        return "Cancelado";
    }
}

// Rol: CONTEXT del State (tiene un estado y le DELEGA cada operacion; no decide
//      nada por si mismo, solo le pasa la pelota al estado actual con "this").
// Rol: SUBJECT del Observer (mantiene la lista de observadores, permite
//      suscribir/desuscribir, y los notifica cuando cambia). Una misma clase
//      cumple los dos roles: es la bisagra que integra los dos patrones.
class Pedido {
    private int id;
    private String cliente;
    private EstadoPedido estado;
    private List<ObservadorDePedido> observadores = new ArrayList<ObservadorDePedido>();

    public Pedido (int id, String cliente){
        this.id =id;
        this.cliente = cliente;
        this.estado = new Pendiente();   // nace Pendiente. OJO: directo, NO por setEstado,
                                         // para no notificar al crear (todavia nadie se suscribio).
    }
    // Unico lugar donde cambia el estado. Por eso metemos aca el aviso: como TODAS
    // las transiciones del State pasan por aca, con notificar una vez alcanza para
    // cubrir confirmar/enviar/entregar/cancelar. Asi se integran State + Observer.
    public void setEstado (EstadoPedido estado){
        this.estado = estado;
        this.notificarObservadores();
    }
    // Las 4 operaciones NO deciden: delegan en el estado actual, pasandole "this"
    // (el propio pedido) para que el estado pueda cambiarlo si corresponde.
    public void confirmar(){
        estado.confirmar(this);
    }
    public void enviar(){
        estado.enviar(this);
    }
    public void entregar(){
        estado.entregar(this);
    }
    public void cancelar(){
        estado.cancelar(this);
    }
    // Recorre la lista y le avisa a cada interesado, pasandose a si mismo (this)
    // para que el observador pueda preguntarle su estado/cliente/id y reaccionar.
    public void notificarObservadores(){
        for(ObservadorDePedido observador : observadores){
            observador.seActualizo(this);
        }
    }
    public void suscribir(ObservadorDePedido observador){    // alta: add a la lista
        this.observadores.add(observador);
    }
    public void desuscribir(ObservadorDePedido observador){  // baja: remove de la lista
        this.observadores.remove(observador);
    }
    // Accessors (solo los que el ejemplo necesita para mostrar datos).
    public String getEstado(){
        return estado.getNombreEstado();
    }
    public int getId(){
        return id;
    }
    public String getCliente(){
        return cliente;
    }
}

// ===================== EJERCICIO 2 - PATRON OBSERVER ========================

// Rol: OBSERVER (la interfaz que cumple todo interesado). Es la interfaz externa
// que el enunciado nos da: la USAMOS, no la implementamos con una clase propia.
// El pedido le llama seActualizo(this) cada vez que cambia de estado.
interface ObservadorDePedido {
    public void seActualizo(Pedido pedido);
}

// ============================================================================
//  EJEMPLO DE INSTANCIACION (Actividad 4)
// ============================================================================
class Demo {
    public static void main(String[] args) {
        Pedido pedido = new Pedido(1, "Juan Perez");

        // Rol: CONCRETE OBSERVER. La lambda es una implementacion al toque de la
        // interfaz ObservadorDePedido: define que hacer cuando el pedido avisa.
        // (Igual que el subsidio/descuento de los parciales anteriores.)
        ObservadorDePedido mailCliente = (p) ->
            System.out.println("  [Mail] Hola " + p.getCliente()
                + ", tu pedido #" + p.getId() + " ahora esta: " + p.getEstado());
        pedido.suscribir(mailCliente);

        System.out.println("Estado inicial: " + pedido.getEstado());
        pedido.confirmar();     // cambia de estado -> setEstado -> avisa: Confirmado
        pedido.enviar();        // -> avisa: Enviado
        pedido.entregar();      // -> avisa: Entregado

        System.out.println("Probando una operacion invalida (entregar recien creado):");
        Pedido otro = new Pedido(2, "Colapinto");
        try {
            otro.entregar();    // Pendiente no puede entregar -> el State lanza la excepcion
        } catch (RuntimeException e) {
            System.out.println("  Excepcion capturada: " + e.getMessage());
        }
    }
}
