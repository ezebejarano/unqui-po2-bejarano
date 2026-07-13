# Justificación de patrones — TP Integrador UNQ-Shop

Para cada módulo: **qué pide el enunciado → problema de diseño → patrón →
por qué (alternativa frágil que evita) → roles según Gamma et al.**

---

## 2.1 Catálogo → Composite (+ Properties para atributos dinámicos)

- **Qué pide:** productos y paquetes; un paquete contiene productos u otros
  paquetes; precio recursivo; mismo contrato para el cliente.
- **Problema:** estructura árbol todo-parte; hojas y nodos deben tratarse igual.
- **Patrón:** Composite. `ItemCatalogo` (interfaz común) → `Producto` (hoja),
  `Paquete` (compuesto con lista de `ItemCatalogo`, precio = Σ hijos × (1−desc)).
  La recursión es natural porque un hijo puede ser otro paquete.
- **Alternativa frágil evitada:** `instanceof Paquete` disperso + recursión a mano;
  se rompe al anidar y viola Open/Closed.
- **Roles GoF:** `ItemCatalogo` = Component · `Producto` = Leaf · `Paquete` =
  Composite · navegador del catálogo = Client.

**Atributos dinámicos (patrón Properties, no GoF):** `Map<String,Object>` en el
producto para atributos que no son campos de clase (Alto, Ancho, Peso). Evita
modificar/recompilar la clase por cada atributo nuevo. La validación recorre
obligatorios + dinámicos y solo chequea que tengan valor asignado.

---

## 2.2 Ciclo de vida del pedido → State

- **Qué pide:** el pedido cambia su comportamiento según el estado; operación
  inválida lanza excepción de dominio propia; transiciones definidas por estado.
- **Problema:** la misma operación hace algo distinto (o es inválida) por estado.
- **Patrón:** State. Interfaz `EstadoPedido`; clases `Borrador`, `Confirmado`,
  `EnPreparacion`, `Enviado`, `Entregado`, `Cancelado`. Cada una sabe sus
  transiciones válidas; las inválidas lanzan la excepción. `Pedido` = contexto
  que delega y cambia de estado.
- **Alternativa frágil evitada:** `switch(estado)` gigante repetido en cada método;
  lógica dispersa, fácil de olvidar casos, agregar estado obliga a tocar todo.
- **Detalle fino:** reembolsos asimétricos — EN_PREPARACION reembolsa producto +
  envío; ENVIADO solo producto. Vive en el `cancelar()` de cada estado. Reembolso
  = Nota de Crédito.
- **Roles GoF:** `EstadoPedido` = State · las 6 clases = ConcreteState · `Pedido`
  = Context.

**State vs Strategy:** misma estructura, distinta intención. Strategy = elegir un
algoritmo intercambiable; State = el objeto cambia de estado y eso altera su
comportamiento.

---

## 2.3 Métodos de envío → Strategy

- **Qué pide:** estándar / express / retiro, con criterios distintos de costo,
  intercambiables.
- **Problema:** el algoritmo de cálculo del costo varía; el pedido solo quiere "un costo".
- **Patrón:** Strategy. Interfaz `MetodoEnvio.calcularCosto(pedido)`. Cada concreto
  encapsula su algoritmo y sus datos: estándar (peso+distancia, `CorreoArgentina
  .estimarEnvio`), express (valor total, `EnvioExpress.calcularCosto`), retiro (0).
- **Alternativa frágil evitada:** `if (tipo == EXPRESS) …` dentro del pedido.
- **Roles GoF:** `MetodoEnvio` = Strategy · 3 concretos = ConcreteStrategy ·
  `Pedido` = Context.

---

## 2.4 Métodos de pago → Template Method

- **Qué pide:** mismos pasos en el mismo orden (validar → reservar → ejecutar →
  notificar), implementados distinto por medio. Notificar tiene default opcional;
  los otros tres son obligatorios.
- **Problema:** la secuencia es fija y compartida; solo cambia cada paso.
- **Patrón:** Template Method. Clase abstracta `MetodoPago` con el template
  `procesarPago()` que llama en orden a `validarDatos()`, `reservarFondos()`,
  `ejecutarTransaccion()` (abstractos) y `notificarResultado()` (hook con default
  = registrar código de transacción). Subclases: Tarjeta, Transferencia, Billetera.
- **Por qué Template Method y no Strategy:** lo invariante es el ORDEN de los pasos.
- **Alternativa frágil evitada:** repetir la orquestación en cada medio con riesgo
  de orden distinto o paso salteado.
- **Roles GoF:** `MetodoPago` = AbstractClass (Template Method + hook) · cada medio
  = ConcreteClass.
- **(*)** Definir una interfaz-API por medio de pago (varios mensajes), sin
  implementarla.

---

## 2.5 Notificaciones del pedido → Observer

- **Qué pide:** ante cada cambio de estado, subsistemas reaccionan independientes;
  el pedido no los conoce ni depende de ellos; deben poder sumarse nuevos.
- **Problema:** uno-a-muchos con acoplamiento débil.
- **Patrón:** Observer. `Pedido` = sujeto con lista de observadores; al cambiar de
  estado notifica (estadoAnterior, estadoNuevo). Observadores: email (solo
  CONFIRMADO/ENVIADO/ENTREGADO), factura (ENTREGADO), fidelización (cupón 5% al
  cancelar).
- **Alternativa frágil evitada:** que el pedido llame directo a cada subsistema;
  lo acopla y obliga a editarlo por cada subsistema nuevo.
- **Roles GoF:** `Pedido` = Subject/ConcreteSubject · `ObservadorPedido` = Observer
  · los 3 subsistemas = ConcreteObserver.

---

## 2.6 Búsqueda en el catálogo → Composite + Specification

- **Qué pide:** criterios simples (nombre, precio máx, categoría, disponibilidad)
  combinables con AND/OR/NOT, anidables sin límite; la búsqueda acepta un único
  criterio y no decide cómo evaluar.
- **Problema:** (a) encapsular "¿cumple?" como objeto; (b) combinar criterios en árbol.
- **Patrón:** Specification + Composite. `Criterio.satisface(item)` (Specification);
  `Y`, `O`, `No` también son criterios que contienen otros (Composite). Se anidan
  libremente. El buscador solo hace `criterio.satisface(item)`.
- **Alternativa frágil evitada:** método de búsqueda con muchos booleanos o parsear
  strings de query.
- **Roles GoF:** `Criterio` = Component · simples = Leaf · `Y`/`O`/`No` = Composite.

---

## 2.8 Reportes → Visitor (+ Bridge/Strategy para el formato)

- **Qué pide:** reporte exportable en texto / CSV / HTML; el núcleo (qué datos y
  cómo estructurarlos) independiente del formato. El enunciado pide investigar Visitor.
- **Problema:** separar qué se reporta de cómo se presenta, agregando formatos sin
  tocar los datos.
- **Patrón:** Visitor. La estructura del reporte hace `aceptar(VisitanteReporte v)`;
  un visitante concreto por formato: `VisitanteTexto`, `VisitanteCSV`,
  `VisitanteHTML`. Formato nuevo = visitante nuevo.
- **Alternativa frágil evitada:** `toTexto()/toCsv()/toHtml()` en cada clase de
  datos; mezcla datos con formato y obliga a editar todo por cada formato.
- **Roles GoF:** `VisitanteReporte` = Visitor · los 3 formatos = ConcreteVisitor ·
  clases del reporte = Element (con `aceptar()`).

---

## Resumen de mapeo

| Módulo | Patrón | Intención |
|--------|--------|-----------|
| 2.1 Catálogo | Composite | Tratar producto y paquete igual; precio recursivo |
| 2.1 Atributos | Properties (Map) | Atributos dinámicos sin tocar la clase |
| 2.2 Pedido | State | Comportamiento y transiciones por estado |
| 2.3 Envío | Strategy | Algoritmo de costo intercambiable |
| 2.4 Pago | Template Method | Secuencia fija, pasos variables, hook opcional |
| 2.5 Notificaciones | Observer | Avisar sin conocer a los subsistemas |
| 2.6 Búsqueda | Composite + Specification | Criterios combinables y anidables |
| 2.8 Reportes | Visitor | Separar datos del formato de salida |
