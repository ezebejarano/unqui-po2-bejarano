# Estructura del repo

Mapa de qué hay en cada carpeta y para qué sirve.

```
Objetos/
├── README.md                          Punto de entrada
├── CLAUDE.md                          Contexto y convenciones del proyecto
├── docs/                              MIGRACION.md · SETUP.md · ESTRUCTURA.md
├── Patrones_Guia_Estudio.md           Guía de patrones (teoría + código)
├── Java_Comandos_Referencia.md        Machete de Java
├── Patrones_de_diseno_-_Gamma_...pdf  Libro GoF (escaneado)
│
├── Parciales/                         11 parciales resueltos + teoría
├── TP integrador/                     Análisis y diagramas de UNQ-Shop
├── Bejarano-Fux-Gulo-TpIntegradorPO2/ Repo grupal (NO versionado acá)
│
├── tp 3/  tp state/  tp strategy/     TPs de cátedra resueltos
├── src/                               Sueltos (ar.edu.unq.po2.tp3.Punto)
└── Agustina/tps/                      TPs de referencia de una compañera
```

---

## `Parciales/` — parciales de práctica

Cada carpeta `Parcial N/` tiene la misma anatomía:

| Archivo | Qué es |
|---|---|
| `enunciado.*` / `*.jpeg` / `*.png` | Enunciado original (foto o scan) |
| `resolucion.html` | Resolución explicada, para leer en el navegador |
| `Parcial_Completo_*.docx` | Versión imprimible A4 (enunciado + solución + teoría) |
| `uml.puml` + `uml.png` | Diagrama de clases de la solución |
| `practica/ParcialN_Practica.java` | Esqueleto para resolverlo a mano, sin mirar |

### Mapeo parcial → patrones

| Parcial | Tema | Patrones |
|---|---|---|
| 2026 | Energía (red de distribución) | Strategy + Composite |
| 2 | Telefonía | Strategy + Composite |
| 3 | Pedidos | State + Observer |
| 4 | Envíos | Template Method |
| 5 | Compañía de seguros | State |
| 6 | Empresa de ingeniería | Composite + Strategy |
| 7 | Supermercado | State + Strategy |
| 8 | Aeropuerto | Observer + Adapter |
| 9 | Logger | Observer + Strategy |
| 10 | Formateo de documentos | Template Method + Adapter |
| 11 | Secretaría de infraestructura | Composite + Adapter |

### Material transversal

- `Teoria_Preguntas_y_Respuestas.docx` — preguntas de teoría con respuesta.
- `Cuadro_Patrones_y_Roles.docx` — cuadro comparativo de los roles GoF de cada patrón
  (Context/Strategy, Subject/Observer, Component/Leaf/Composite, etc.).
- `Parciales_5_a_11_Completo.docx` — los 7 últimos parciales en un solo documento.
- `Parciales sueltos/Analisis_Parciales.md` — **el más útil para estudiar**: análisis de parciales
  viejos reales (Seguros con los criterios de corrección del docente, Máquina Expendedora,
  App de diagramas UML, FileSystem del recuperatorio dic-2025) + un método para encarar cualquier
  parcial mapeando palabras clave del enunciado → patrón.
- `parciales mas/parciales/` — código fuente crudo de los parciales 6 a 11, tal como se resolvieron
  (proyectos sueltos con su `src/` y su UML).

## `TP integrador/` — UNQ-Shop

Sistema de e-commerce, **solo capa de dominio** (sin GUI, sin persistencia, sin frameworks).
El entregable real se sube al **repo grupal**; esta carpeta es el análisis y el material de defensa.

| Archivo | Qué es |
|---|---|
| `TP_Integrador_POO_Ecommerce.docx` | Enunciado original de la cátedra |
| `RESUMEN.md` | Análisis del enunciado módulo por módulo (empezar acá) |
| `Patrones_Justificacion.md` | Por qué cada patrón en cada módulo, con roles GoF |
| `UML_Plan.md` | Plan de los diagramas |
| `diagramas/` | 8 diagramas PlantUML (`.puml` + `.png` + `.svg`) |
| `src/` + `test/` | Implementación de referencia (módulo Catálogo, 15 tests) |
| `tools/TestRunner.java` | Runner de JUnit 5 por consola |
| `modificaciones y nueva clase/` | Código pasado al grupo (Catálogo y Ciclo de vida del pedido) |
| `Speech_Defensa_*.docx/pdf` | Guiones para la defensa oral |
| `plantuml.jar` | PlantUML 1.2024.7 |

### Módulo → patrón

| Módulo | Patrón |
|---|---|
| 2.1 Catálogo de productos | **Composite** + atributos dinámicos (Properties, `Map<String,Object>`) |
| 2.2 Ciclo de vida del pedido | **State** |
| 2.3 Envío | **Strategy** |
| 2.4 Pago | **Template Method** |
| 2.5 Notificaciones | **Observer** |
| 2.6 Búsqueda y filtrado | **Composite** + Specification |
| 2.8 Reportes | **Visitor** (+ Bridge/Strategy para el formato texto/CSV/HTML) |

## `Bejarano-Fux-Gulo-TpIntegradorPO2/` — repo grupal

**Es un repositorio git independiente**, excluido por el `.gitignore` de acá.
Remote: `https://github.com/FuxAgustina/Bejarano-Fux-Gulo-TpIntegradorPO2`.
Se commitea y pushea por separado — ver [MIGRACION.md](MIGRACION.md).

Convenciones del **grupo** (distintas a las de este repo): packages `TPintegrador.<Modulo>`,
una carpeta por módulo, clase abstracta `Catalogo` en vez de interfaz.

## TPs de cátedra

| Carpeta | Patrón | Contenido |
|---|---|---|
| `tp 3/` | — | Intro a Java (PDF) |
| `tp state/` | **State** | Máquina de videojuego: 6 clases en `src/`, UML, `TP_State_resolucion.md` |
| `tp strategy/` | **Strategy** | Encriptación: 6 clases + 4 tests (17/17 en verde), UML, resolución |
| `src/` | — | `ar.edu.unq.po2.tp3.Punto` suelto |

## `Agustina/tps/` — TPs de referencia

Resoluciones de una compañera, organizadas por patrón: `TPComposite/`, `TPObserver/`, `TPState/`,
`TPStrategy/`, `TPTemplateAdapter/`, `TestYTestDoubles/`, `tpSOLID/`, más `tp2/` a `tp5/`.
Sirven para comparar enfoques. Varias traen su `.drawio` con el UML.

> `Agustina/tps/tp4/src/Supermercado.lnk` es un acceso directo de Windows roto en cualquier
> máquina que no sea la original. El código real está en `tp4/src/supermercado/`.
