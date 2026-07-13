// =============================================================================
//  PARCIAL 2026 - PRACTICA  (Empresa de energia electrica)
//  Escribi TODA tu solucion en ESTE archivo, como si fuera la hoja del parcial.
//  Cuando quieras que lo verifique, guarda y decime "verifica": yo lo compilo,
//  te digo si compila y te corrijo la logica.
//
//  REGLA para que compile todo junto en un archivo:
//   - No pongas "public" en las clases/interfaces (asi pueden convivir todas aca).
//   - Para el ejemplo, hace una clase normal (sin public) con:  public static void main(String[] args) { ... }
// =============================================================================
//
//  ------------------------------ EJERCICIO 1 ---------------------------------
//  Una empresa de energia monitorea medidores. De cada medidor se conoce:
//  codigo de identificacion, direccion, zona energetica, mes-anio y consumo (kWh).
//
//  Cada medidor tiene su forma de calculo de tarifa (se define al crearlo), que
//  debe poder CAMBIARSE en tiempo de ejecucion y permitir AGREGAR nuevas formas.
//  Formas iniciales:
//   - Tarifa Residencial Estandar: cargo fijo $500 + $12 por cada kWh.
//   - Tarifa Industrial por Bloques: sin cargo fijo; $15 por kWh si el consumo
//     es <= 1000, y $25 por kWh SOLO para el excedente si supera 1000.
//
//  Descuento por subsidio: usar (NO implementar) esta interfaz externa, que
//  devuelve el % de descuento segun la zona (ej 0.15), o 0 si no hay subsidio:
//       float obtenerDescuento(ZonaEnergetica zona);
//
//  Costo final del medidor = costo bruto segun su tarifa, y sobre ese valor se
//  aplica el descuento del subsidio usando la zona del medidor.
//
//  ------------------------------ EJERCICIO 2 ---------------------------------
//  La empresa agrupa medidores en Redes de Distribucion. Una red puede contener
//  medidores Y otras subredes. Cada red tiene un nombre.
//  Calcular costo total y consumo total de cualquier infraestructura de forma
//  homogenea:
//   - Medidor: consumo = el registrado; costo = el del Ejercicio 1.
//   - Red: consumo total = suma de sus elementos; costo total = suma de sus
//     elementos + un costo fijo de mantenimiento propio + 3% de recargo sobre
//     la suma de sus elementos.
//
//  ------------------------------- ACTIVIDADES --------------------------------
//   1) Diagrama de clases UML integrado de ambos ejercicios.
//   2) Patrones usados + roles segun Gamma et al.
//   3) Implementar en Java todo lo necesario, con constructores. Solo los
//      accessors que necesites.
//   4) Ejemplo de instanciacion: una red con un medidor y una subred, y la
//      subred con dos medidores; mezcla tarifas; mostra consumo total y costo
//      total de la red.
//
//  Pista (checklist de clases, tapala si queres practicar sin ayuda):
//    [ ] EstrategiaCalculo (interface)      [ ] Punto (abstracta)
//    [ ] TarifaResidencial                  [ ] Medidor extends Punto
//    [ ] TarifaIndustrial                   [ ] RedDeDistribucion extends Punto
//    [ ] ZonaEnergetica                     [ ] clase con main() para el ejemplo
//    [ ] SubsidioCorporativo (interface, NO implementar)
// =============================================================================

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

// ============================================================================
//  ESCRIBI TU SOLUCION DE ACA PARA ABAJO
// ============================================================================

interface EstrategiaDeCalculo{
    public float calculoDeCosto(float consumo);

}

class TarifaResidencial implements EstrategiaDeCalculo{
    private float costoFijo = 500.0f;
    private float costoPorKwh = 12.0f;

    public float calculoDeCosto(float consumo){
        return costoFijo + costoPorKwh * consumo;
    }
}

class TarifaIndustrial implements EstrategiaDeCalculo{
    private float costoPorKwhSinExcedente = 15.0f;
    private float costoPorKwhConExcedente = 25.0f;
    private float limite = 1000.0f;

    public float calculoDeCosto(float consumo){
        if (consumo <= limite){
            return costoPorKwhSinExcedente * consumo;
        }
        else{
            float excedente = consumo - limite;
            return costoPorKwhSinExcedente * 1000.0f + costoPorKwhConExcedente * excedente;
        }
    }

}

class ZonaEnergetica{
    private String nombre;

    public ZonaEnergetica(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
      return this.nombre;   
    }
}

interface SubsidioCorporativo{
    public float obtenerDescuento(ZonaEnergetica zona);
}

class Medidor extends Punto{
    private int codigoIdentificacion;
    private String direccion;
    private ZonaEnergetica zonaEnergetica;
    private LocalDate MesAnio;
    private float consumo;
    private EstrategiaDeCalculo estrategiaDeCalculo;
    private SubsidioCorporativo subsidio;

    public Medidor(int codigoIdentificacion, String direccion, ZonaEnergetica zonaEnergetica, LocalDate MesAnio, float consumo, EstrategiaDeCalculo estrategiaDeCalculo, SubsidioCorporativo subsidio){
        this.codigoIdentificacion = codigoIdentificacion;
        this.direccion = direccion;
        this.zonaEnergetica = zonaEnergetica;
        this.MesAnio = MesAnio;
        this.consumo = consumo;
        this.estrategiaDeCalculo = estrategiaDeCalculo;
        this.subsidio = subsidio;
    }   

    public float calculoDeTarifa(float consumo){
        return estrategiaDeCalculo.calculoDeCosto(consumo);
    }

    public float costoFinal(){
        return this.calculoDeTarifa(consumo) * (1 - subsidio.obtenerDescuento(zonaEnergetica));
    }
    public float consumoTotal(){
        return consumo;
    }
}

abstract class Punto{
    public abstract float costoFinal();
    public abstract float consumoTotal();
}

class RedDeDistribucion extends Punto{
    private String nombre;
    private float costoFijoMantenimiento;
    private List<Punto> puntos = new ArrayList<>();

    public RedDeDistribucion(String nombre, float costoFijoMantenimiento){
        this.nombre = nombre;
        this.costoFijoMantenimiento = costoFijoMantenimiento;
    }
    public void agregarPunto(Punto punto){
        puntos.add(punto);
    }
    public void eliminarPunto(Punto punto){
        puntos.remove(punto);
    }
    public float consumoTotal(){
        float total = 0;
        for (int i=0; i<puntos.size(); i++){
            total = total + puntos.get(i).consumoTotal();
        }
        return total;
    }
    public float costoFinal(){
        float total = 0;
        for (int i=0; i<puntos.size(); i++){
            total = total + puntos.get(i).costoFinal();
        }
        return total + costoFijoMantenimiento + (total * 0.03f);
    }
}

// ============================================================================
//  EJEMPLO DE INSTANCIACION
// ============================================================================
class Demo {
    public static void main(String[] args) {
    SubsidioCorporativo sinSubsidio = (zona) -> 0f;
    LocalDate fecha = LocalDate.of(2026, 5, 1);
    ZonaEnergetica zona=new ZonaEnergetica("Avellaneda");
    Medidor M1 = new Medidor(1, "Calle 1", zona, fecha, 800.0f, new TarifaResidencial(), sinSubsidio);
    Medidor M2 = new Medidor(2, "Calle 2", zona, fecha, 1200.0f, new TarifaIndustrial(), sinSubsidio);                
    Medidor M3 = new Medidor(3, "Calle 3", zona, fecha, 500.0f, new TarifaResidencial(), sinSubsidio);
    RedDeDistribucion red1 = new RedDeDistribucion("Red 1", 1000.0f);
    RedDeDistribucion red2 = new RedDeDistribucion("Red 2", 500.0f);

    red1.agregarPunto(M1);
    red2.agregarPunto(M2);
    red2.agregarPunto(M3);
    red1.agregarPunto(red2);

    float consumoTotalRed1 = red1.consumoTotal();
    float costoTotalRed1 = red1.costoFinal();
    float consumoTotalRed2 = red2.consumoTotal();
    float costoTotalRed2 = red2.costoFinal();

    System.out.println("Consumo total: " + red1.consumoTotal());
    System.out.println("Costo total:   " + red1.costoFinal());
    }
}

