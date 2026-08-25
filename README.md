# Programación con Objetos 2 — UNQ (Ezequiel Bejarano)

Material de estudio, TPs y práctica de parciales de **Programación con Objetos 2** (Universidad
Nacional de Quilmes). Todo el código es **Java puro** (capa de dominio, sin GUI ni frameworks),
con tests en **JUnit 5** y diagramas UML generados con **PlantUML**.

- **Repo personal (este):** https://github.com/ezebejarano/unqui-po2-bejarano
- **Repo grupal del TP Integrador:** https://github.com/FuxAgustina/Bejarano-Fux-Gulo-TpIntegradorPO2
  → se clona **aparte**, no está incluido acá (ver [docs/MIGRACION.md](docs/MIGRACION.md)).

> ⚠️ **¿Venís de otra máquina?** Empezá por [docs/MIGRACION.md](docs/MIGRACION.md) — es el
> checklist completo para dejar todo funcionando de cero.

---

## Documentación

| Archivo | Para qué sirve |
|---|---|
| [docs/MIGRACION.md](docs/MIGRACION.md) | Checklist para mover/levantar todo en una máquina nueva |
| [docs/SETUP.md](docs/SETUP.md) | Entorno: JDK, Eclipse, JUnit, PlantUML, poppler. Cómo compilar y correr tests |
| [docs/ESTRUCTURA.md](docs/ESTRUCTURA.md) | Mapa carpeta por carpeta de todo el repo |
| [CLAUDE.md](CLAUDE.md) | Contexto del proyecto y convenciones (lo lee Claude Code al abrir la carpeta) |

## Material de estudio

| Archivo | Contenido |
|---|---|
| [Patrones_Guia_Estudio.md](Patrones_Guia_Estudio.md) | Guía completa de patrones: cómo funciona, cuándo usarlo, ventajas/desventajas, ejemplo real y código Java |
| [Java_Comandos_Referencia.md](Java_Comandos_Referencia.md) | Machete de métodos y comandos Java (compilar, correr, tests, colecciones, streams) |
| [Patrones_de_diseno_-_Gamma_Helm_Johnson_Vlissides.pdf](Patrones_de_diseno_-_Gamma_Helm_Johnson_Vlissides.pdf) | Libro GoF (escaneado, sin capa de texto — ver nota en docs/SETUP.md) |
| [Parciales/Parciales sueltos/Analisis_Parciales.md](Parciales/Parciales%20sueltos/Analisis_Parciales.md) | Análisis de parciales viejos + método para encarar cualquier parcial (palabras clave → patrón) |

## Contenido

### `Parciales/` — 11 parciales de práctica resueltos
Cada uno con enunciado, solución en Java, UML (`.puml` + `.png`), resolución en HTML/DOCX y guías
paso a paso. Mapeo parcial → patrón en [docs/ESTRUCTURA.md](docs/ESTRUCTURA.md).

Además: `Teoria_Preguntas_y_Respuestas.docx`, `Cuadro_Patrones_y_Roles.docx` (cuadro comparativo de
roles GoF) y `Parciales_5_a_11_Completo.docx` (compilado de los 7 últimos).

### `TP integrador/` — UNQ-Shop (e-commerce)
Análisis, diagramas y código de referencia del TP Integrador. El **entregable real vive en el repo
grupal**; acá están el enunciado, el [RESUMEN.md](TP%20integrador/RESUMEN.md), la
[justificación de patrones](TP%20integrador/Patrones_Justificacion.md), los 8 diagramas PlantUML
y el speech de defensa.

### TPs de cátedra
`tp 3/`, `tp state/`, `tp strategy/`, `src/` — resoluciones propias con UML y tests.

### `Agustina/` — TPs de referencia
Resoluciones de una compañera, organizadas por patrón. Útiles para comparar enfoques.

## Patrones cubiertos

Strategy · State · Observer · Composite · Template Method · Adapter · Visitor · Chain of Responsibility


---

## Arranque rápido — correr los tests del TP Integrador

```powershell
# Desde "TP integrador/". Arma el classpath de JUnit 5 y compila dominio + tests.
$jars = (Get-ChildItem "$env:USERPROFILE\.p2\pool\plugins" -Filter *.jar |
  Where-Object { $_.Name -match '(junit-(jupiter|platform).*_(5\.14\.4|1\.14\.4)|org\.opentest4j_|org\.apiguardian\.api_)' -and $_.Name -notmatch 'source' } |
  ForEach-Object FullName) -join ";"

$src = Get-ChildItem -Recurse src,test,tools -Filter *.java | ForEach-Object FullName
javac -d bin -cp "$jars" $src
java -cp "bin;$jars" TestRunner ar.edu.unq.po2.unqshop    # -> 15/15 tests successful
```

Detalle completo (instalación del JDK, Eclipse, PlantUML) en [docs/SETUP.md](docs/SETUP.md).
