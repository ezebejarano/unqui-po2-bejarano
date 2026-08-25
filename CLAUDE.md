# Contexto del proyecto

Repo de estudio de **Programación con Objetos 2 (UNQ)** — patrones de diseño en **Java + Eclipse**.
Material para TPs y práctica de parciales. Autor: Ezequiel Bejarano (`ezebejarano`).

Ver también: [README.md](README.md) · [docs/SETUP.md](docs/SETUP.md) ·
[docs/ESTRUCTURA.md](docs/ESTRUCTURA.md) · [docs/MIGRACION.md](docs/MIGRACION.md)

---

## Patrones en foco (8)

Strategy · State · Observer · Adapter · Template Method · Composite · Visitor ·
Chain of Responsibility

## Convenciones de código

- **Java puro**, capa de dominio: sin GUI, sin persistencia, sin frameworks.
- Packages `ar.edu.unq.po2.*` (ej. `ar.edu.unq.po2.unqshop.catalogo`).
  Excepción: el **repo grupal** usa `TPintegrador.<Modulo>`.
- **Un archivo por clase/interfaz**, listo para pegar en Eclipse.
- Preferir **`if/else` explícito** en lugar del operador ternario.
- Tests con **JUnit 5 (Jupiter)**.
- Nombres, comentarios y mensajes de commit **en español**.

## Cómo explicar

Formato preferido para cada patrón:
**explicación breve → ejemplos → cuándo conviene → ventajas/desventajas → código**.

El código tiene que poder seguirse paso a paso; el usuario suele agregar sus propios comentarios
encima.

## Commits

- Identidad: `ezebejarano <ezequielbejarano98@gmail.com>` (configurar `user.name`/`user.email`
  local en cada repo).
- **Nunca agregar el trailer `Co-Authored-By: Claude`** ni ninguna mención a IA.
  El TP se corrige por participación individual según los commits de cada integrante:
  que figure una IA como coautor lo perjudica. Los commits salen 100% a nombre del usuario.
- Mensajes en español, descriptivos.

## Los dos repos

| Repo | Remote | Qué contiene |
|---|---|---|
| Este (`Objetos/`) | `ezebejarano/unqui-po2-bejarano` | Todo el material personal de estudio |
| `Bejarano-Fux-Gulo-TpIntegradorPO2/` | `FuxAgustina/Bejarano-Fux-Gulo-TpIntegradorPO2` | Entrega grupal del TP Integrador |

El segundo está en el `.gitignore` del primero: **hay que commitear y pushear en los dos por
separado.**

---

## Estado del TP Integrador (UNQ-Shop)

E-commerce, solo dominio. Entrega por GitHub, se pide cobertura de tests ≥ 95 %.
Fechas: 1ra el 1-jul-2026, 2da el 16-jul-2026.

**Mapeo módulo → patrón:** 2.1 Catálogo = Composite (+ atributos dinámicos vía `Map`/Properties) ·
2.2 Pedido = State · 2.3 Envío = Strategy · 2.4 Pago = Template Method ·
2.5 Notificaciones = Observer · 2.6 Búsqueda = Composite + Specification ·
2.8 Reportes = Visitor (+ Bridge/Strategy para el formato).

**Implementado y verde:** módulo 2.1 Catálogo en `TP integrador/src/.../catalogo/`
(`ItemCatalogo`, `Producto`, `Paquete`, `SKU`, `Categoria`, `ValidacionInvalidaException`),
**15/15 tests**.

**Decisión de diseño clave del Catálogo:** `getPrecioBase()` de `Producto` devuelve el precio
**con** promoción (precio final). El `Paquete` suma los precios base de sus hijos y aplica
`× (1 − descuento)` → las promos se acumulan. Verificado contra el ejemplo del enunciado:
Pack Audio Móvil = auriculares 8000 + funda 1500 + cable 800 = 10300, −15 % → **8755**.

**Nota sobre el repo grupal:** el grupo usa clase abstracta `Catalogo` (no interfaz) y una carpeta
por módulo. Hubo un problema conocido: `Pedido.java` importaba clases del módulo State que todavía
no estaban subidas → el repo no compilaba hasta que las subieran.

---

## Notas del entorno

Detalle completo en [docs/SETUP.md](docs/SETUP.md). Lo que suele tropezar:

- En `~/.p2/pool/plugins` conviven **JUnit 5.14.4 y 6.1.0** — filtrar por versión al armar el
  classpath o rompe.
- **No hay Graphviz.** PlantUML funciona igual con el motor **Smetana** (Java puro): agregar
  `!pragma layout smetana` en la primera línea del `.puml`.
- El PDF del libro GoF y varios enunciados son **escaneos sin capa de texto**: hay que renderizarlos
  a PNG con `pdftoppm` (poppler) y leer la imagen.
- Para SVG → PNG: Edge headless, con ruta de salida **sin espacios**.
