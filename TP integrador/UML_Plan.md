# Plan de UML — TP Integrador UNQ-Shop

Estrategia: **7 diagramas por módulo + 1 de integración**. Cada bloque trae el
PlantUML listo para renderizar. Las decisiones de diseño que el diagrama fija
están al final.

Convención de packages: `ar.edu.unq.po2.unqshop.<modulo>`.

---

## 0. Integración (cómo `Pedido` conecta todo)

`Pedido` es el centro del sistema:
- **Context** del State (`EstadoPedido`).
- **Subject** del Observer (`ObservadorPedido`).
- **Client** del Strategy de envío (`MetodoEnvio`).
- **Client** del Template Method de pago (`MetodoPago`).
- Contiene `LineaPedido` que referencian `ItemCatalogo` (Composite del catálogo).

```plantuml
@startuml Integracion
hide empty members
class Pedido
interface EstadoPedido
interface MetodoEnvio
abstract MetodoPago
interface ObservadorPedido
class LineaPedido
interface ItemCatalogo
class Inventario
class Catalogo
interface Criterio
abstract Reporte

Pedido "1" o-- "1" EstadoPedido : estadoActual
Pedido "1" o-- "1" MetodoEnvio
Pedido "1" o-- "0..1" MetodoPago
Pedido "1" o-- "*" ObservadorPedido : observadores
Pedido "1" *-- "*" LineaPedido
LineaPedido --> ItemCatalogo
Pedido ..> Inventario : decrementa/repone stock
Catalogo "1" o-- "*" ItemCatalogo
Catalogo ..> Criterio : buscar(criterio)
Reporte ..> ItemCatalogo : recolecta ventas
@enduml
```

---

## 1. Catálogo → Composite (+ Properties)

```plantuml
@startuml Catalogo
hide empty members
interface ItemCatalogo {
  +getNombre(): String
  +getDescripcion(): String
  +getPrecioBase(): double
  +perteneceA(c: Categoria): boolean
  +hayStockDisponible(inv: Inventario): boolean
  +validar(): void
}
class Producto {
  -sku: SKU
  -nombre: String
  -marca: Marca
  -categoria: Categoria
  -precio: double
  -precioFinal: double
  -atributos: Map<String,Object>
  +setAtributo(clave: String, valor: Object)
  +getAtributo(clave: String): Object
  +validar(): void
}
class Paquete {
  -nombre: String
  -descripcion: String
  -descuento: double
  +agregar(item: ItemCatalogo)
  +getPrecioBase(): double
}
class Catalogo {
  +agregar(item: ItemCatalogo)
  +buscar(criterio: Criterio): List<ItemCatalogo>
}
class SKU
enum Categoria { ELECTRONICA \n INDUMENTARIA \n HOGAR \n COCINA }
class Marca
class ValidacionInvalidaException

ItemCatalogo <|.. Producto
ItemCatalogo <|.. Paquete
Paquete "1" o-- "*" ItemCatalogo : items
Catalogo "1" o-- "*" ItemCatalogo
Producto --> SKU
Producto --> Categoria
Producto --> Marca
Producto ..> ValidacionInvalidaException
@enduml
```

- `Producto` = Leaf · `Paquete` = Composite · `ItemCatalogo` = Component.
- `Paquete.getPrecioBase()` = Σ hijos.getPrecioBase() × (1 − descuento).
- Atributos dinámicos = `Map<String,Object>` (patrón Properties).
- `validar()` chequea obligatorios (nombre, SKU) + claves dinámicas con valor.

---

## 2. Ciclo de vida del pedido → State

```plantuml
@startuml Pedido
hide empty members
class Pedido {
  -lineas: List<LineaPedido>
  -estado: EstadoPedido
  -notasCredito: List<NotaCredito>
  +agregarItem(item: ItemCatalogo, cant: int)
  +quitarItem(item: ItemCatalogo)
  +confirmar()
  +cancelar()
  +prepararEnvio()
  +enviar()
  +entregar()
  +cambiarEstado(nuevo: EstadoPedido)
  +totalProductos(): double
  +totalConEnvio(): double
}
interface EstadoPedido {
  +agregarItem(p: Pedido, item, cant)
  +confirmar(p: Pedido)
  +cancelar(p: Pedido)
  +prepararEnvio(p: Pedido)
  +enviar(p: Pedido)
  +entregar(p: Pedido)
  +nombre(): String
}
abstract EstadoBase {
  ' lanza OperacionInvalidaException por defecto
}
class Borrador
class Confirmado
class EnPreparacion
class Enviado
class Entregado
class Cancelado
class LineaPedido {
  -item: ItemCatalogo
  -cantidad: int
  +subtotal(): double
}
class NotaCredito {
  -monto: double
  -motivo: String
}
class OperacionInvalidaException

EstadoPedido <|.. EstadoBase
EstadoBase <|-- Borrador
EstadoBase <|-- Confirmado
EstadoBase <|-- EnPreparacion
EstadoBase <|-- Enviado
EstadoBase <|-- Entregado
EstadoBase <|-- Cancelado
Pedido "1" o-- "1" EstadoPedido
Pedido "1" *-- "*" LineaPedido
Pedido "1" *-- "*" NotaCredito
EstadoBase ..> OperacionInvalidaException
Confirmado ..> Inventario : decrementa stock
Cancelado ..> Inventario : repone stock
@enduml
```

- Transiciones válidas viven en cada ConcreteState.
- `cancelar()` genera `NotaCredito`; reembolso asimétrico:
  EN_PREPARACION = producto + envío · ENVIADO = solo producto.

---

## 3. Métodos de envío → Strategy

```plantuml
@startuml Envio
hide empty members
interface MetodoEnvio {
  +calcularCosto(p: Pedido): double
  +estimarDias(): String
}
class EnvioEstandar {
  +calcularCosto(p: Pedido): double  ' usa peso + Direccion
  +estimarDias(): String             ' 5-7 días
}
class EnvioExpress {
  -cargoBase: double
  -porcentaje: double
  +calcularCosto(p: Pedido): double  ' % sobre total + base
  +estimarDias(): String             ' 1 día
}
class RetiroEnSucursal {
  -sucursal: Sucursal
  +calcularCosto(p: Pedido): double  ' 0
  +estimarDias(): String             ' inmediato / hasta 3 días
}
class CorreoArgentina <<library>> {
  +{static} estimarEnvio(peso: float, dir: Direccion): float
}
class Sucursal

MetodoEnvio <|.. EnvioEstandar
MetodoEnvio <|.. EnvioExpress
MetodoEnvio <|.. RetiroEnSucursal
EnvioEstandar ..> CorreoArgentina
RetiroEnSucursal --> Sucursal
Pedido "1" o-- "1" MetodoEnvio
@enduml
```

- `CorreoArgentina` es librería externa provista (solo se usa, no se implementa).

---

## 4. Métodos de pago → Template Method

```plantuml
@startuml Pago
hide empty members
abstract MetodoPago {
  +{method} procesarPago(p: Pedido): void  <<final, template>>
  #{abstract} validarDatos(): void
  #{abstract} reservarFondos(): void
  #{abstract} ejecutarTransaccion(): void
  #notificarResultado(): void   ' hook con default (registra cod. transacción)
}
class PagoTarjetaCredito
class PagoTransferencia
class PagoBilleteraVirtual
interface APITarjetaCredito {
  +validarTarjeta(num, cvv, venc): boolean
  +preautorizar(monto): void
  +transferir(monto): void
}
interface APITransferencia {
  +validarCBUyAlias(cbu, alias): boolean
  +transferir(monto): void
}
interface APIBilletera {
  +validarSaldo(monto): boolean
  +bloquearSaldo(monto): void
  +acreditar(monto): void
  +notificarPush(): void
}
class Comprobante

MetodoPago <|-- PagoTarjetaCredito
MetodoPago <|-- PagoTransferencia
MetodoPago <|-- PagoBilleteraVirtual
PagoTarjetaCredito ..> APITarjetaCredito
PagoTransferencia ..> APITransferencia
PagoBilleteraVirtual ..> APIBilletera
MetodoPago ..> Comprobante : genera y registra
@enduml
```

- `procesarPago()` = Template Method (orden fijo). `notificarResultado()` = hook.
- Las interfaces API se **definen, no se implementan** (requisito del enunciado).

---

## 5. Notificaciones del pedido → Observer

```plantuml
@startuml Notificaciones
hide empty members
interface ObservadorPedido {
  +actualizar(p: Pedido, anterior: EstadoPedido, nuevo: EstadoPedido)
}
class NotificadorEmail {
  ' solo CONFIRMADO, ENVIADO, ENTREGADO
}
class GeneradorFactura {
  ' solo ENTREGADO
}
class Fidelizacion {
  ' al CANCELADO: cupón 5%
}
interface MailSender <<library>> {
  +enviarMail(destino, titulo, mensaje, adjunto)
}
class Factura
class Cupon

ObservadorPedido <|.. NotificadorEmail
ObservadorPedido <|.. GeneradorFactura
ObservadorPedido <|.. Fidelizacion
Pedido "1" o-- "*" ObservadorPedido
NotificadorEmail ..> MailSender
Fidelizacion ..> MailSender
GeneradorFactura ..> Factura
Fidelizacion ..> Cupon
@enduml
```

- `Pedido` (Subject) notifica `(estadoAnterior, estadoNuevo)` en `cambiarEstado()`.

---

## 6. Búsqueda → Composite + Specification

```plantuml
@startuml Busqueda
hide empty members
interface Criterio {
  +satisface(item: ItemCatalogo): boolean
}
class CriterioNombre {
  -texto: String
}
class CriterioPrecioMaximo {
  -maximo: double
}
class CriterioCategoria {
  -categoria: Categoria
}
class CriterioDisponibilidad {
  -inventario: Inventario
}
class CriterioY {
  -criterios: List<Criterio>
}
class CriterioO {
  -criterios: List<Criterio>
}
class CriterioNo {
  -criterio: Criterio
}

Criterio <|.. CriterioNombre
Criterio <|.. CriterioPrecioMaximo
Criterio <|.. CriterioCategoria
Criterio <|.. CriterioDisponibilidad
Criterio <|.. CriterioY
Criterio <|.. CriterioO
Criterio <|.. CriterioNo
CriterioY "1" o-- "*" Criterio
CriterioO "1" o-- "*" Criterio
CriterioNo "1" o-- "1" Criterio
Catalogo ..> Criterio : buscar(c)
@enduml
```

- Simples = Leaf · `CriterioY`/`CriterioO`/`CriterioNo` = Composite. Anidan sin límite.

---

## 7. Reportes → Visitor

```plantuml
@startuml Reportes
hide empty members
abstract Reporte {
  +generar(): List<ElementoReporte>
  +exportar(v: VisitanteFormato): String
}
class ReporteProductosMasVendidos
interface ElementoReporte {
  +aceptar(v: VisitanteFormato): void
}
class Encabezado
class Fila {
  -item: ItemCatalogo
  -unidadesVendidas: int
  -precioPromedio: double
}
class Pie
interface VisitanteFormato {
  +visitarEncabezado(e: Encabezado)
  +visitarFila(f: Fila)
  +visitarPie(p: Pie)
  +resultado(): String
}
class VisitanteTexto
class VisitanteCSV
class VisitanteHTML
class RegistroVenta {
  -item: ItemCatalogo
  -cantidad: int
  -precioCobrado: double
  -fecha: LocalDate
}

Reporte <|-- ReporteProductosMasVendidos
ElementoReporte <|.. Encabezado
ElementoReporte <|.. Fila
ElementoReporte <|.. Pie
VisitanteFormato <|.. VisitanteTexto
VisitanteFormato <|.. VisitanteCSV
VisitanteFormato <|.. VisitanteHTML
Reporte "1" o-- "*" ElementoReporte
ElementoReporte ..> VisitanteFormato : aceptar(v)
ReporteProductosMasVendidos ..> RegistroVenta
@enduml
```

- `VisitanteFormato` = Visitor · Texto/CSV/HTML = ConcreteVisitor ·
  Encabezado/Fila/Pie = Element (doble despacho con `aceptar`).

---

## Clases de apoyo (transversales)

- `Cliente` (nombre, email), `Direccion`, `Sucursal`, `Deposito`.
- `Inventario`: mapea `Producto × Deposito → cantidad`; `hayStock`, `decrementar`,
  `reponer`. Consultado por State (módulo 2) y CriterioDisponibilidad (módulo 6).
- `SKU`, `Marca`, `Categoria` (enum), `NotaCredito`, `Comprobante`, `Factura`,
  `Cupon`, `RegistroVenta`.

## Decisiones de diseño fijadas por el UML

1. Precio de paquete usa el **precio final** de cada ítem × (1 − descuento del paquete).
2. `ItemCatalogo` expone `perteneceA` y `hayStockDisponible` para que los criterios
   funcionen uniformemente sobre productos y paquetes (Composite).
3. El **stock** vive en `Inventario`, no en el Producto.
4. `Pedido` es Context + Subject + Client de envío y pago a la vez.
5. El pedido contiene `LineaPedido` (ítem + cantidad), no ítems sueltos.
6. `EstadoBase` (clase abstracta) lanza `OperacionInvalidaException` por defecto;
   cada estado concreto solo redefine sus operaciones válidas.
```
