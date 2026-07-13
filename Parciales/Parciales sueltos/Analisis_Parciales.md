# Análisis de parciales — Objetos 2 (UNQ)

Estos parciales cubren los mismos patrones que el TP Integrador. Para cada uno:
**de qué va → qué patrón → cómo encararlo → puntos clave / trampas**.

> Mapa rápido de patrones por parcial:
>
> | Parcial | Dominio | Patrón principal | Patrón secundario |
> |---------|---------|------------------|-------------------|
> | Compañía de Seguros | Póliza Abierta/Cerrada/Vigente | **State** | (usa una interfaz API: NO es Template Method) |
> | Máquina Expendedora | 3 "momentos" de la máquina | **State** | Adapter (interfaces Pantalla/Dispatcher) |
> | App de diagramas UML / José & Co | Clases-Paquetes / Depto-Oficina | **Composite** | Visitor (pseudocódigo), Strategy (almacenamiento) |
> | FileSystem (recuperatorio) | Archivos/carpetas/comprimidos | **Composite** | **Observer** (gestores), Adapter (OneDrive.upload) |

---

## 1. Compañía de Seguros (State) — *con criterios de corrección*

**Dominio:** una Póliza tiene un inventario de ítems (cantidad × valor) y gastos
administrativos. Pasa por fases **Abierta → Cerrada → Vigente** y cada fase habilita
distintas operaciones (agregarItem, cerrarInventario, pagar, cancelar).

**Patrón: State.** Cada fase es una clase con su propio comportamiento.

**Cómo encararlo:**
1. `Poliza` (contexto) con `inventario` (colección), `gastosAdministrativos` (colección)
   y una referencia `estado`.
2. Interfaz/clase abstracta `EstadoPoliza` con los métodos: `agregarItem`,
   `cerrarInventario`, `pagar`, `cancelar`, `aplicarDescuento`.
3. Estados concretos: `Abierta`, `Cerrada`, `Vigente`.
4. `Poliza` **delega** en su estado; el estado hace la transición.

**Criterios de corrección del docente (textual, lo más importante):**
- **State:** los métodos del estado **deben recibir la Póliza como parámetro** para
  poder pedirle el cambio de estado. (Alternativa: pasarle la póliza al crear el estado).
- Implementar los **métodos por defecto como vacíos** en una **superclase abstracta**
  (o métodos `default` en la interfaz) para **no repetir los vacíos** en cada subclase.
  → *"Las operaciones no mencionadas no surten efecto"* = método vacío heredado.
- Cada concrete class implementa solo **la lógica de los métodos que correspondan**.
- `agregarItem` **devuelve la Póliza** (`return this`) para poder encadenar llamadas
  (*"operación que se espera invocar varias veces"*).
- **`precio = montoAsegurado × 0.0075`** (0,75 %), ¡NO × 0.75! ← error que penalizan.
- `Inventario` y `gastosAdministrativos` como **colecciones**.
- **Bonificación: NO es Template Method.** El método `bonificar` vive en la `Poliza`
  (o en un colaborador `Bonificador`), y **solo el paso "aplicar descuento" se delega
  al estado**. La Póliza debe tener una **variable tipada `BonificacionService`**
  (la interfaz provista) — esto es un punto importante.
- Lo que está "en rosa" (UML correcto, el paso 2 del descuento, tipar con la interfaz)
  si está mal **te deja muy cerca del desaprobado**, sin importar el puntaje.

> **Lección clave:** no metas Template Method porque "hay pasos". Si la secuencia y la
> responsabilidad son del objeto principal y solo *un* paso varía por estado → eso es
> **State**, no Template Method. El docente lo dice explícito: *"No debe haber un
> strategy en la solución"* y *"los pasos no conforman un template method"*.

---

## 2. Máquina Expendedora (State)

**Dominio:** una máquina con **dos stocks** (actual y máximo) y **3 momentos**:
1. Espera que el usuario ingrese una ficha → imprime "Elija su producto".
2. Espera que elija producto (si hay stock, despacha y descuenta; si no, "Fuera de
   servicio" y se apaga).
3. "Reponer stock" → recarga y vuelve a funcionar.
- Ignora fichas/acciones que no correspondan al momento actual.

**Patrón: State** (los 3 momentos) + **Adapter** sobre las interfaces provistas
`Pantalla.imprimir(mensaje)` y `Dispatcher.dispatchProduct()` (se usan, no se implementan).

**Cómo encararlo:**
1. `MaquinaExpendedora` (contexto) con `stockActual`, `stockMaximo`, `estado`,
   referencias a `Pantalla` y `Dispatcher`.
2. `EstadoMaquina` (abstracta con métodos vacíos por defecto) → `EsperandoFicha`,
   `EsperandoSeleccion`, `FueraDeServicio`.
3. Cada estado implementa solo lo que corresponde; los mensajes ignorados = vacío heredado.
4. Transiciones: insertarFicha, seleccionarProducto, reponerStock.

> Mismo esquema que Seguros: **estado abstracto con vacíos + concretos con su lógica +
> contexto que delega**. Es la plantilla mental para cualquier State.

---

## 3. App de diagramas UML / José & Co (Composite)

**Dominio:** modelar diagramas UML compuestos por **Clases, Relaciones y Paquetes**;
un **Paquete agrupa** clases, relaciones y **otros paquetes** (recursivo). El ejemplo
"José & Co Construcciones": un Departamento General contiene oficinas y **otros
departamentos** (Departamento Operativo → más oficinas), y cada oficina tiene empleados.

**Patrón: Composite** + dos colaboradores:
- **Visitor** para "generar el pseudocódigo del diagrama" (recorrer la estructura y
  producir texto).
- **Strategy/Adapter** para el **almacenamiento**: `File.save(unaRuta, unContenido)`
  vs `GoogleDrive.upload(unToken, unaRuta, unContenido)` con timeout. Distintos backends
  detrás de una interfaz común.

**Cómo encararlo:**
1. Interfaz `ElementoDiagrama` (o `Componente`) con el contrato común.
2. Hojas: `Clase`, `Relacion`. Compuesto: `Paquete` con `List<ElementoDiagrama>`.
3. Funcionalidad "cantidad de clases", "paquete con más clases" → recursión Composite.
4. La consigna pide **constructores** (≠ al de Seguros que los omitía por tiempo).

> Trampa de instanciación (tarea 3): armar el árbol a mano. Practicá escribir el
> `new Paquete(...)` anidado que arma exactamente la estructura descrita.

---

## 4. FileSystem — Recuperatorio dic-2025 (Composite + Observer)

**Dominio:** archivos y carpetas; una carpeta contiene archivos **u otras carpetas**.
Existen **archivos comprimidos** (con una tasa de compresión). El mensaje `#size`
calcula el tamaño recursivamente:
- Archivo normal → su tamaño.
- Carpeta → suma de los tamaños de su contenido.
- Comprimido → tamaño del contenido × tasa de compresión.

Además, los elementos **notifican cambios a "gestores"**: ante un cambio se puede
**subir a la nube** (`OneDrive.upload(file_or_folder, account_key)`) y/o **loguear**
en consola. Puede haber **muchos gestores** por elemento, distintos y desconocidos a futuro.

**Patrones:**
- **Composite** (archivos/carpetas/comprimidos, `#size` recursivo).
- **Observer** (gestores que reaccionan a cambios; un elemento = sujeto, gestores =
  observers; "muchos gestores, distintos, agregables a futuro" = la pista textual de Observer).
- **Adapter** sobre `OneDrive.upload` (servicio externo).

**Cómo encararlo:**
1. `ElementoFS` (interfaz) con `size()` y la lógica de suscripción de gestores.
2. Hojas: `Archivo`. Compuestos: `Carpeta` (lista), `ArchivoComprimido` (envuelve y
   aplica la tasa).
3. `Gestor` (interfaz observer) con `actualizar(elemento)`; concretos `GestorUpload`
   (con su `account_key`) y `GestorLogueo`.
4. Tarea 3: instanciar el ejemplo y **suscribir** los gestores (ej.: carpeta OO2 con
   upload a objetos2unq@gmail.com + logueo).

> Pista para reconocer **Observer** en cualquier parcial: *"varios subsistemas reaccionan
> de forma independiente"*, *"podrían agregarse otros a futuro"*, *"el X no debe conocer
> a los Y"*. Igual que el punto 2.5 del TP integrador.

---

## Respuestas a la teoría (parcial de Seguros)

1. **4 secciones de un test de unidad (en orden):**
   A) Setup / Arrange · B) Exercise / Act · C) Verify / Assert · D) Teardown.
2. **¿Un mismo objeto puede tener distintos tipos durante su ciclo de vida?** →
   **Verdadero.** Una misma instancia conforma a su clase y a **todos** sus supertipos
   (superclases e interfaces), y puede referenciarse con variables de esos distintos tipos.
3. **"Las interfaces solo tipan parámetros, no variables."** → **Falso.** Las interfaces
   también pueden tipar variables (y atributos, y retornos).
4. **Observer: el Observable sabe…** → **D) Ninguna.** El sujeto solo conoce la interfaz
   `Observer`; no sabe la clase concreta de sus observadores ni qué harán al ser notificados.
5. **"No es necesario que la jerarquía del Strategy sea polimórfica."** → **Falso.** El
   Strategy se apoya justamente en el polimorfismo (todas las estrategias comparten la
   interfaz y se usan de forma intercambiable).
6. **"Como en Strategy, las subclases de un State nunca instancian a sus hermanas."** →
   **Falso.** En el **State** una subclase **sí** instancia a una hermana para hacer la
   **transición** al siguiente estado. En Strategy no hace falta.
7. **`CA ca = new CA(); Cuenta c = ca;` ¿c y ca son instancias diferentes?** → **Falso
   (No).** Es **una sola** instancia referenciada por dos variables de distinto tipo.
8. **¿Qué patrón forma una estructura de árbol al instanciarse?** → **C) Composite.**

---

## Método para encarar CUALQUIER parcial de esta materia

1. **Detectá el patrón por las palabras clave del enunciado:**
   - "pasa por fases / estados / momentos", "según la fase, la operación…" → **State**.
   - "agrupa varios, y uno puede contener otros del mismo tipo", "recursivo", "árbol" → **Composite**.
   - "varios subsistemas reaccionan", "agregables a futuro", "no debe conocer a" → **Observer**.
   - "los mismos pasos en el mismo orden, cada uno distinto" → **Template Method**.
   - "distintas formas de calcular/hacer lo mismo, intercambiables" → **Strategy**.
   - "una librería/servicio externo con otra interfaz" (te dan la firma) → **Adapter**.
   - "recorrer la estructura para producir/exportar algo" → **Visitor**.
2. **UML primero** (lo piden siempre como tarea 1). Cuidá: cardinalidades, sentido de
   las relaciones, colecciones como `*`, y **tipar con la interfaz del patrón**.
3. **Indicá patrones + roles según Gamma** (tarea 2 siempre). Ej.: "State: `Poliza`=Context,
   `EstadoPoliza`=State, `Abierta/Cerrada/Vigente`=ConcreteState".
4. **Implementá en Java** delegando del contexto al colaborador del patrón.
5. **Test de unidad** del caso que piden (Setup-Exercise-Verify-Teardown).

**Errores que penalizan (vistos en las correcciones):**
- Confundir State con Template Method o meter un Strategy de más.
- No tipar la variable con la interfaz provista (ej. `BonificacionService`).
- Repetir métodos vacíos en cada estado en vez de heredarlos de una abstracta.
- No hacer la transición de estado (o no pasarle el contexto al estado).
- Cuentas mal (0,75 % = ×0.0075).
