// =============================================================================
//  PARCIAL 4 - PRACTICA  (Empresa de envios / paqueteria)
//  Escribi TODA tu solucion en ESTE archivo, como si fuera la hoja del parcial.
//  Cuando quieras que lo verifique, guarda y decime "verifica".
//
//  REGLA para que compile todo junto en un archivo:
//   - No pongas "public" en las clases (asi conviven todas aca).
//   - Para el ejemplo, hace una clase (sin public) con: public static void main(String[] args){...}
// =============================================================================
//
//  ------------------------- EJERCICIO 1 (Template Method) --------------------
//  Calcular el COSTO TOTAL de un envio (se conoce: peso en kg, destinatario).
//  El calculo sigue SIEMPRE los mismos pasos y en el mismo orden:
//    1) costo base            (depende del tipo)
//    2) + recargo por peso    (depende del tipo)
//    3) + seguro              (OPCIONAL: por defecto 0)
//    4) al subtotal (1+2+3) se le aplica 21% de IVA  -> ese es el costo total
//  El esqueleto (pasos y orden) es FIJO e igual para todos; solo cambia cuanto
//  vale cada paso. Tipos iniciales:
//    - Envio Estandar: base $500 ; recargo $100 por kg ; sin seguro.
//    - Envio Express : base $1200; recargo $200 por kg ; sin seguro.
//  Debe poder agregarse un tipo nuevo sin repetir/tocar el esqueleto.
//
//  ------------------------- EJERCICIO 2 (usar el hook) -----------------------
//  Nuevo tipo Envio Internacional, que SI cobra seguro:
//    - base $3000 ; recargo $150 por kg ; seguro = 5% de (base + recargo).
//  Agregalo aprovechando el paso opcional (seguro) sin tocar el esqueleto.
//
//  ------------------------------- ACTIVIDADES --------------------------------
//   1) UML integrado.
//   2) Patron + roles (Gamma). Indica: metodo plantilla, pasos primitivos y hook.
//   3) Implementar en Java con constructores. Solo los accessors que necesites.
//   4) Ejemplo: un envio de cada tipo; mostrar el costo total de cada uno.
//
//  Pista (checklist, tapala si queres practicar sin ayuda):
//    [ ] Envio (abstracta): costoTotal() final + pasos abstractos + hook seguro()
//    [ ] EnvioEstandar / EnvioExpress / EnvioInternacional (extends Envio)
//    [ ] clase con main() para el ejemplo
// =============================================================================


// ============================================================================
//  ESCRIBI TU SOLUCION DE ACA PARA ABAJO
// ============================================================================

abstract class Envio {
    protected double peso;          
    protected String destinatario;

    public Envio(double peso, String destinatario) {
        this.peso = peso;
        this.destinatario = destinatario;
    }
    public final double costoTotal() {
        double subtotal = this.costoBase() + this.recargoPorPeso() + this.seguro();
        return subtotal * 1.21;     
    }
    protected abstract double costoBase();        
    protected abstract double recargoPorPeso();   
    protected double seguro() { return 0.0; };
}

class EnvioEstandar extends Envio {
    public EnvioEstandar(double peso, String destinatario) {
        super(peso, destinatario); 
    }
    protected double costoBase(){
        return 500.0; 
    }
    protected double recargoPorPeso() {
        return 100.0 * this.peso; 
    }
}

class EnvioExpress extends Envio {
    public EnvioExpress(double peso, String destinatario) { 
        super(peso, destinatario); 
    }
    protected double costoBase(){ 
        return 1200.0; 
    }
    protected double recargoPorPeso() {
         return 200.0 * this.peso; 
        }
}

class EnvioInternacional extends Envio {
    public EnvioInternacional(double peso, String destinatario) { 
        super(peso, destinatario); 
    }
    protected double costoBase(){ 
        return 3000.0;
    }
    protected double recargoPorPeso() {
         return 150.0 * this.peso; 
    }
    protected double seguro() {                                   
        return (this.costoBase() + this.recargoPorPeso()) * 0.05; 
    }
}

class Demo {
    public static void main(String[] args) {
        Envio e1 = new EnvioEstandar(2, "Juan Perez");
        Envio e2 = new EnvioExpress(2, "Colapinto");
        Envio e3 = new EnvioInternacional(2, "Checo Perez");

        System.out.println("Estandar:      " + e1.costoTotal());
        System.out.println("Express:       " + e2.costoTotal());
        System.out.println("Internacional: " + e3.costoTotal());
    }
}



