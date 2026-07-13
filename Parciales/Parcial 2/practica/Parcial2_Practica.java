// =============================================================================
//  PARCIAL 2 - PRACTICA  (Empresa de telefonia movil)
//  Escribi TODA tu solucion en ESTE archivo. Cuando quieras, decime "verifica".
//  Recorda: NO pongas "public" en las clases/interfaces (para que entren todas
//  en un archivo). El ejemplo va en una clase con: public static void main(...)
//
//  --- EJERCICIO 1 (Strategy) ---
//  Lineas telefonicas. De cada linea: numero, titular, categoria de cliente,
//  minutos consumidos. Cada linea tiene un plan de facturacion CAMBIABLE en
//  runtime y se deben poder agregar planes nuevos.
//   - Plan Prepago: sin cargo fijo. $8 por minuto.
//   - Plan Pospago por Tramos: cargo fijo $2000. Los primeros 500 min incluidos;
//     cada minuto que exceda 500 se cobra $5.
//  Descuento por fidelidad: interfaz externa (usar, NO implementar):
//     float obtenerDescuento(CategoriaCliente categoria);   // ej 0.10 ; 0 si no aplica
//  Costo final de la linea = costo bruto del plan, con el descuento aplicado.
//
//  --- EJERCICIO 2 (Composite) ---
//  Cuentas Corporativas que agrupan lineas Y sub-cuentas. Cada cuenta tiene nombre.
//  Calcular costo total y total de minutos de forma homogenea:
//   - Linea: minutos = los consumidos; costo = el del Ej1.
//   - Cuenta: minutos = suma de sus elementos; costo = suma de sus elementos +
//     cargo fijo administrativo propio + 2% de recargo sobre la suma.
//
//  ACTIVIDADES: 1) UML  2) patrones+roles  3) codigo Java (con constructores)
//               4) ejemplo: cuenta con una linea y una sub-cuenta (con 2 lineas),
//                  mezclando planes; mostrar minutos y costo total.
// =============================================================================

import java.util.ArrayList;
import java.util.List;

// ============================================================================
//  ESCRIBI TU SOLUCION DE ACA PARA ABAJO
// ============================================================================


// ===================== EJERCICIO 1 - PATRON STRATEGY ========================

// Rol: STRATEGY (la interfaz del algoritmo intercambiable).
// Define QUE se puede pedir (calcular el pago segun los minutos), pero no COMO.
// Cada plan concreto resuelve el como a su manera. Gracias a esta interfaz, la
// linea puede tratar a todos los planes por igual y cambiarlos en runtime.
interface PlanFacturacion {
    public float calculoDePago(int minutosConsumidos);
}

// Rol: CONCRETE STRATEGY. Una forma concreta de calcular el pago.
class PlanPrepago implements PlanFacturacion {
    private float costoPorMinuto = 8.0f;

    public float calculoDePago(int minutosConsumidos) {
        return minutosConsumidos * costoPorMinuto;
    }
}

// Rol: CONCRETE STRATEGY. Otra forma concreta (por tramos, con excedente).
class PlanPospagoPorTramos implements PlanFacturacion {
    private float cargoFijo = 2000.0f;
    private int minutosIncluidos = 500;
    private float costoPorMinutoExcedente = 5.0f;

    public float calculoDePago(int minutosConsumidos){
        if (minutosConsumidos <= minutosIncluidos){
            return cargoFijo;
        }
        else {
            int minutosExcedidos = minutosConsumidos - minutosIncluidos;
            return cargoFijo + minutosExcedidos * costoPorMinutoExcedente;
        }
    }
}

// Rol: CONTEXT del Strategy (tiene un PlanFacturacion y le delega el calculo;
// puede cambiarlo en runtime con setPlanFacturacion).
// Rol: LEAF del Composite (es un Componente pero NO contiene otros; es un
// elemento final del arbol). Por eso extiende Componente e implementa costoTotal
// y minutosTotales.
class LineaTelefonica extends Componente {
    private int numero;
    private String titular;
    private CategoriaCliente categoriaCliente;
    private int minutosConsumidos;
    private PlanFacturacion planFacturacion;      // la Strategy que tiene puesta
    private DescuentoFidelidad descuentoFidelidad; // colaborador externo

    public LineaTelefonica(int numero, String titular, CategoriaCliente categoriaCliente, int minutosConsumidos,
                             PlanFacturacion planFacturacion, DescuentoFidelidad descuentoFidelidad){
        this.numero = numero;
        this.titular = titular;
        this.categoriaCliente = categoriaCliente;
        this.minutosConsumidos = minutosConsumidos;
        this.planFacturacion = planFacturacion;
        this.descuentoFidelidad = descuentoFidelidad;
    }
    public void setPlanFacturacion(PlanFacturacion planFacturacion){
        this.planFacturacion = planFacturacion;   // cambio de estrategia en runtime
    }
    public float calculoDePago(int minutosConsumidos){
        return planFacturacion.calculoDePago(minutosConsumidos);  // delega en la Strategy
    }
    public float obtenerDescuento(CategoriaCliente categoria){
        return descuentoFidelidad.obtenerDescuento(categoria);    // usa el colaborador externo
    }
    // Metodo del Componente (Composite): el costo de una hoja es su costo final.
    public float costoTotal(){
        return this.calculoDePago(minutosConsumidos) * (1 - this.obtenerDescuento(categoriaCliente));
    }
    // Metodo del Componente (Composite): los minutos de una hoja son los que consumio.
    public int minutosTotales(){
        return minutosConsumidos;
    }
}

// Clase auxiliar: la categoria del cliente (la usa el descuento de fidelidad).
class CategoriaCliente {
    private String nombreCategoria;

    public CategoriaCliente(String nombreCategoria){
        this.nombreCategoria = nombreCategoria;
    }
    public String getNombreCategoria() {
        return nombreCategoria;
    }
}

// Interfaz externa (colaborador): se USA, NO se implementa. Es un servicio de
// afuera que devuelve el descuento segun la categoria del cliente.
interface DescuentoFidelidad {
    public float obtenerDescuento(CategoriaCliente categoria);
}

// ===================== EJERCICIO 2 - PATRON COMPOSITE =======================

// Rol: COMPONENT (el tipo comun del Composite). NO contiene nada: solo define el
// contrato que comparten la hoja (LineaTelefonica) y el compuesto (CuentaCorporativa).
// Gracias a este tipo comun, una cuenta puede tener en su lista tanto lineas como
// otras cuentas y tratarlas igual.
abstract class Componente {
    public abstract float costoTotal();
    public abstract int minutosTotales();
}

// Rol: COMPOSITE. Contiene una lista de Componentes (que pueden ser lineas u otras
// cuentas). Calcula costo y minutos delegando de forma recursiva en sus hijos.
class CuentaCorporativa extends Componente {
    private String nombre;
    private float cargoFijoAdministrativo;
    private float recargo = 0.02f;                     // 2% de recargo por gestion
    private List<Componente> componentes = new ArrayList<Componente>();

    public CuentaCorporativa(String nombre, float cargoFijoAdministrativo){
        this.nombre = nombre;
        this.cargoFijoAdministrativo = cargoFijoAdministrativo;
    }
    public void agregarComponente(Componente componente){
        this.componentes.add(componente);
    }
    public void eliminarComponente(Componente componente){
        this.componentes.remove(componente);
    }
    // Costo total: suma del costo de los hijos + cargo fijo + 2% sobre la suma.
    public float costoTotal(){
        float totalCosto = 0;
        for (int i = 0; i < componentes.size(); i++){
            totalCosto = totalCosto + componentes.get(i).costoTotal();
        }
        return totalCosto + cargoFijoAdministrativo + (totalCosto * recargo);
    }
    // Minutos totales: la suma de los minutos de los hijos (recursivo).
    public int minutosTotales(){
        int totalMinutos = 0;
        for (int i = 0; i < componentes.size(); i++){
            totalMinutos = totalMinutos + componentes.get(i).minutosTotales();
        }
        return totalMinutos;
    }
}

// ============================================================================
//  EJEMPLO DE USO (main) - NO MODIFICAR
// ============================================================================
class Demo {
    public static void main(String[] args) {
    CategoriaCliente categoria1 = new CategoriaCliente("categoria1");
    DescuentoFidelidad fidelidad = (cat) -> 0.0f;
    LineaTelefonica T1 = new LineaTelefonica(1161576655, "Juan Perez", categoria1, 600, new PlanPospagoPorTramos(),fidelidad);
    LineaTelefonica T2 = new LineaTelefonica(1166676655, "Colapinto", categoria1, 500, new PlanPospagoPorTramos(), fidelidad);
    LineaTelefonica T3 = new LineaTelefonica(1161576000, "Checo Perez", categoria1, 600, new PlanPrepago(),fidelidad);

    CuentaCorporativa cuenta1 = new CuentaCorporativa("Cuenta 1", 1000.0f);
    CuentaCorporativa cuenta2 = new CuentaCorporativa("Cuenta 2", 500.0f);

    cuenta1.agregarComponente(T1);
    cuenta2.agregarComponente(T2);
    cuenta2.agregarComponente(T3);
    cuenta1.agregarComponente(cuenta2);

    float consumoTotalCuenta1 = cuenta1.minutosTotales();
    float costoTotalCuenta1 = cuenta1.costoTotal();
    float consumoTotalCuenta2 = cuenta2.minutosTotales();
    float costoTotalCuenta2 = cuenta2.costoTotal();

    System.out.println("Consumo total: " + cuenta1.minutosTotales());
    System.out.println("Costo total:   " + cuenta1.costoTotal());
    }
}
