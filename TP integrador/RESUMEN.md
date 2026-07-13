# Resumen — TP Integrador POO · UNQ-Shop

> Sistema de e-commerce. Se implementa **solo la capa de dominio en Java**:
> sin interfaces gráficas, sin persistencia, sin frameworks. Solo clases,
> interfaces y pruebas. El objetivo es **demostrar la correcta aplicación de
> los principios de diseño OO**, justificando cada decisión.

---

## 1. Contexto y alcance

- Empresa de venta online: gestiona catálogo, procesa pedidos, calcula envío y pago.
- **Capa de dominio únicamente.** No construir una app completa.
- Cada decisión de diseño debe justificarse: qué problema resuelve y qué
  alternativa más frágil evita.

---

## 2. Módulos funcionales (7) y patrón sugerido

### 2.1 Catálogo de productos → **Composite** (+ atributos dinámicos)
- Dos tipos de elementos: **productos individuales** y **paquetes**.
- Producto: SKU, Nombre, Marca, Categoría, precio y **precio final**
  (precio con descuento promocional solo de ese producto).
- **Atributos dinámicos:** la solución debe ser escalable y permitir agregar
  atributos dinámicos que NO sean atributos de una clase del modelo
  (ej.: Alto, Ancho, Peso). → Sugerido: `Map<String, Object>` (patrón Properties).
- **Validación:** comprobar que los atributos obligatorios (nombre, SKU) y los
  dinámicos tengan un valor asignado (solo se chequea la asignación, no el contenido).
- **Paquete:** agrupa varios ítems (productos u otros paquetes) y aplica un
  descuento sobre la suma de sus partes.
- El cliente NO necesita saber si consulta un producto o un paquete: mismo contrato.
- Toda entidad expone: **nombre, descripción y precio base calculado**.
- Precio del paquete = (suma de precios de sus ítems) × (1 − descuento%).
- Debe funcionar **recursivamente** con paquetes anidados (ej. Kit Home Office
  que contiene el Pack Audio Móvil + teclado + mouse).

**Ejemplo:** Pack Audio Móvil = Auriculares ($8.000) + Funda ($1.500) + Cable ($800)
= $10.300, con 15% desc → **$8.755**.

### 2.2 Ciclo de vida del pedido → **State**
- El pedido cambia su comportamiento según el estado. Operación inválida →
  lanzar **excepción de dominio propia** (no genérica).
- Los reembolsos se resuelven generando y registrando una **Nota de Crédito**.

| Estado | Descripción | Transiciones válidas |
|--------|-------------|----------------------|
| **BORRADOR** | Cliente arma el pedido. Solo aquí se agregan/quitan ítems. | → CONFIRMADO, → CANCELADO |
| **CONFIRMADO/PAGO** | Cliente confirmó. Se **decrementa el stock**. | → EN_PREPARACION, → CANCELADO |
| **EN_PREPARACION** | Depósito prepara el envío. Si se cancela: **repone stock y reembolsa producto + envío**. | → ENVIADO, → CANCELADO |
| **ENVIADO** | En camino. Si se cancela: **solo reembolsa producto, NO el envío**. | → ENTREGADO, → CANCELADO |
| **ENTREGADO** | Cliente recibió. **Estado terminal.** | (ninguna) |
| **CANCELADO** | Cancelado. **Terminal.** Si venía de Confirmado, **incrementa stock**. | (ninguna) |

### 2.3 Métodos de envío → **Strategy**
- **Envío estándar:** costo según peso total (kg) y distancia (km) al destino.
  Estimación fija 5–7 días hábiles.
  Usar librería dada: `float CorreoArgentina.estimarEnvio(float peso, Direccion direccionEnvio)`.
- **Envío express:** % fijo sobre el valor total del pedido + cargo base.
  Entrega garantizada en 1 día hábil.
  Usar: `float EnvioExpress.calcularCosto(float precio)`.
- **Retiro en sucursal:** costo siempre cero. Días según stock en sucursal:
  inmediato si hay stock local, hasta 3 días si requiere traslado interno.

### 2.4 Métodos de pago → **Template Method**
Mismos pasos, mismo orden, implementación distinta por medio:
validar datos → reservar fondos → ejecutar transacción → notificar resultado.

| Paso | Tarjeta de crédito | Transferencia bancaria | Billetera virtual |
|------|--------------------|------------------------|-------------------|
| Validar | N.º tarjeta, CVV, vencimiento | CBU/CVU y alias válido | Saldo suficiente |
| Reservar | Pre-autorización al banco | No aplica | Bloqueo del saldo |
| Ejecutar | Transferencia inmediata | Inmediata o programada | Acreditación en tiempo real |
| Notificar | Cupón imprimible | Comprobante CBU + n.º op | Notificación push |

- El paso **notificar tiene implementación por defecto** (registro del código de
  transacción) que las subclases pueden personalizar sin estar obligadas.
- Validar/reservar/ejecutar son **obligatorios** y específicos de cada medio.
- (*) Definir **una interfaz por medio de pago** (API con varios mensajes) para
  validar y disparar estas operaciones. **No hay que implementarlas, solo definirlas.**

### 2.5 Notificaciones del pedido → **Observer**
- Cada cambio de estado: distintos subsistemas reaccionan de forma independiente.
- El módulo de pedidos NO conoce ni depende de los subsistemas: solo comunica el
  cambio, indicando **estado anterior y nuevo**.
- Subsistemas actuales:
  - **Notificador de email:** correo al cliente. Solo en CONFIRMADO, ENVIADO, ENTREGADO.
  - **Generador de factura:** comprobante fiscal cuando llega a ENTREGADO.
  - **Fidelización:** si se cancela, mensaje con cupón de descuento del 5%.
- Interfaz dada: `MailSender.enviarMail(direcciónDestino, título, mensaje, adjunto)`.
- Deben poder agregarse nuevos subsistemas a futuro (aún desconocidos).

### 2.6 Búsqueda en el catálogo → **Composite + Specification**
- Cada criterio es una unidad independiente que sabe si un ítem lo satisface.
- **Criterios simples:**
  - Por nombre (contiene texto, sin distinción de mayúsculas).
  - Por precio máximo (≤ valor).
  - Por categoría.
  - Por disponibilidad (stock en al menos un depósito).
- **Criterios complejos (operadores lógicos, también son criterios):**
  - **AND:** satisface todos.
  - **OR:** satisface al menos uno.
  - **NOT:** no satisface el criterio envuelto.
- Se anidan libremente a cualquier profundidad.
- La interfaz de búsqueda **acepta un único criterio**; el cliente construye el
  criterio compuesto antes de invocar. El módulo de búsqueda no decide cómo evaluar.

### 2.8 Reportes → **Visitor** (+ Strategy/Bridge para formato)
> El enunciado dice explícitamente: *investigue el patrón Visitor*.
- Cada reporte puede exportarse en varios formatos: **texto plano, CSV y HTML**.
- El núcleo del reporte (qué datos recolectar y cómo estructurarlos) debe ser
  **independiente del formato** de salida.
- **Reporte requerido:** productos más vendidos — ordena ítems (productos y
  paquetes) por unidades vendidas en un período, incluye **precio promedio
  efectivamente cobrado** (puede diferir del precio base por descuentos de paquete).

> Nota: el enunciado salta de 2.6 a 2.8 (no hay 2.7).

---

## 4. Entregables
1. **Diagrama de clases UML** de la solución completa.
2. **Documentación PDF:** integrantes + emails, decisiones de diseño, detalles de
   implementación relevantes, **patrones usados y roles según Gamma et. al.**
3. **Implementación en Java** con **tests de unidad ≥ 95% de cobertura** del dominio.
4. Todo en un **repositorio git** accesible por docentes, con seguimiento del trabajo.

> Participación **equitativa** según commits con usuarios correspondientes
> (discrepancias → desaprobación personal). Sin interfaces gráficas.

---

## 5. Fechas y modalidad
- **1ra fecha:** Miércoles 1 de Julio 2026, 23:59 hs.
- **2da fecha:** Jueves 16 de Julio 2026.
- Entregas en **GitHub**.
- Corrección offline + **entrevista individual remota** por integrante. En la
  entrevista hay que tener un **IDE con el TP abierto y ejecutable**.

---

## Mapa rápido de patrones (lo que se evalúa)

| Módulo | Patrón | Rol clave |
|--------|--------|-----------|
| 2.1 Catálogo | **Composite** | Producto = hoja, Paquete = compuesto |
| 2.1 Atributos | **Properties** (Map) | Atributos dinámicos sin tocar la clase |
| 2.2 Pedido | **State** | Cada estado define transiciones válidas |
| 2.3 Envío | **Strategy** | Algoritmo de costo intercambiable |
| 2.4 Pago | **Template Method** | Esqueleto fijo, pasos variables; notificar con hook |
| 2.5 Notificaciones | **Observer** | Pedido = sujeto, subsistemas = observers |
| 2.6 Búsqueda | **Composite + Specification** | Criterios simples y compuestos anidables |
| 2.8 Reportes | **Visitor** + Strategy/Bridge | Núcleo del reporte vs. formato de salida |
