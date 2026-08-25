# Setup del entorno

Todo lo que hace falta instalar y los comandos para compilar, testear y generar diagramas.
Los valores de abajo son los **verificados** en la máquina original.

---

## 1. Java

- **JDK 26.0.1** (`openjdk 26.0.1 2026-04-21`), instalado en `C:\Program Files (x86)\java\jdk-26.0.1`.
- Cualquier JDK ≥ 17 alcanza para todo el material.

```powershell
java -version
javac -version
```

Si no está en el PATH: agregar `<JDK>\bin` a las variables de entorno del usuario.

## 2. Eclipse

- **Eclipse IDE for Java Developers / Committers**, versión `2026-06`,
  en `C:\Users\maria\eclipse\committers-2026-06`.
- El workspace vive en `.metadata/` dentro de la carpeta del repo — **está en `.gitignore`**,
  se regenera solo la primera vez que abrís Eclipse.

Convención de packages del repo: `ar.edu.unq.po2.*`
(ej. `ar.edu.unq.po2.strategy`, `ar.edu.unq.po2.unqshop.catalogo`).

## 3. JUnit 5 (Jupiter)

No hace falta instalar nada aparte: **Eclipse ya trae los jars**, en
`%USERPROFILE%\.p2\pool\plugins`. Versiones usadas: **JUnit Jupiter 5.14.4** +
**JUnit Platform 1.14.4**, más `org.opentest4j` y `org.apiguardian.api`.

> ⚠️ En esa carpeta conviven 5.14.4 y 6.1.0. **Hay que filtrar por versión**, si se mezclan
> las dos el classpath rompe. El comando de abajo ya lo hace.

### Correr los tests por consola

Desde `TP integrador/` (mismo esquema sirve para `tp strategy/`):

```powershell
# 1) Classpath de JUnit (filtrado a 5.14.4 / 1.14.4, sin los -source)
$jars = (Get-ChildItem "$env:USERPROFILE\.p2\pool\plugins" -Filter *.jar |
  Where-Object { $_.Name -match '(junit-(jupiter|platform).*_(5\.14\.4|1\.14\.4)|org\.opentest4j_|org\.apiguardian\.api_)' -and $_.Name -notmatch 'source' } |
  ForEach-Object FullName) -join ";"

# 2) Compilar dominio + tests + runner
$src = Get-ChildItem -Recurse src,test,tools -Filter *.java | ForEach-Object FullName
javac -d bin -cp "$jars" $src

# 3) Ejecutar (el argumento es el paquete a descubrir)
java -cp "bin;$jars" TestRunner ar.edu.unq.po2.unqshop
```

`tools/TestRunner.java` es un runner propio: usa `LauncherFactory` + `selectPackage(...)` y
imprime el resumen. Recibe el paquete base como argumento (por defecto
`ar.edu.unq.po2.unqshop`).

Salida esperada hoy: **15 tests found · 15 successful · 0 failed** (módulo Catálogo).

## 4. PlantUML — diagramas UML

El jar está versionado en `TP integrador/plantuml.jar` (`plantuml-1.2024.7`), así que **no hay que
bajar nada**.

**No hay Graphviz (`dot`) instalado y no hace falta:** los `.puml` del repo usan el motor
**Smetana** (Java puro), declarado con `!pragma layout smetana` en la primera línea.

```powershell
cd "TP integrador"
java -jar plantuml.jar -tsvg diagramas/01_catalogo.puml
java -jar plantuml.jar -tpng diagramas/01_catalogo.puml

# Todos de una
java -jar plantuml.jar -tpng diagramas/*.puml
```

Si algún `.puml` nuevo falla con un error de `dot`, agregarle como primera línea:

```
!pragma layout smetana
```

## 5. Leer los PDFs escaneados (opcional)

El PDF del libro GoF y algunos enunciados de parcial son **imágenes sin capa de texto**: no se
pueden copiar ni buscar. Para leerlos hay que renderizarlos a PNG con **poppler**:

```powershell
winget install oschwartz10612.Poppler
pdftoppm -png -r 150 -f 1 -l 3 "archivo.pdf" "salida"
```

Para convertir SVG → PNG sin instalar nada, Edge en modo headless:

```powershell
& "C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe" --headless=new --screenshot="C:\temp\out.png" --window-size=1600,1200 "file:///C:/temp/in.svg"
```

> La ruta de salida **no puede tener espacios**, Edge la corta.

---

## Resumen de versiones

| Herramienta | Versión | Ubicación |
|---|---|---|
| JDK | 26.0.1 | `C:\Program Files (x86)\java\jdk-26.0.1` |
| Eclipse | committers-2026-06 | `C:\Users\maria\eclipse\committers-2026-06` |
| JUnit Jupiter | 5.14.4 | `%USERPROFILE%\.p2\pool\plugins` |
| JUnit Platform | 1.14.4 | `%USERPROFILE%\.p2\pool\plugins` |
| PlantUML | 1.2024.7 (motor Smetana) | `TP integrador/plantuml.jar` |
| Poppler | vía winget | `...\WinGet\Packages\oschwartz10612.Poppler...` |
