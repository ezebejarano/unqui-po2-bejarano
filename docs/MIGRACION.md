# Migración a una máquina nueva

Checklist para levantar todo el material de Programación con Objetos 2 desde cero.
Seguilo en orden: al terminar tenés los dos repos clonados, el entorno Java andando y
los tests en verde.

---

## 0. Antes de apagar la máquina vieja

- [ ] **Cerrar todos los Word/Excel abiertos** (dejan archivos lock `~$...` que ensucian el repo).
- [ ] `git status` en la carpeta `Objetos/` → commitear y pushear todo lo pendiente.
- [ ] `git status` en `Objetos/Bejarano-Fux-Gulo-TpIntegradorPO2/` → **es otro repo**, se commitea
      y pushea aparte. Verificar con `git log origin/main..main` que no queden commits sin subir.
- [ ] Confirmar que OneDrive terminó de sincronizar (ícono sin flechas).

Lo que **no** hace falta llevar (se regenera solo): `.metadata/` (workspace de Eclipse),
`bin/`, `*.class`, `.idea/`, `*.iml`.

---

## 1. Clonar los repos

```powershell
cd "$env:USERPROFILE\OneDrive\Documentos\Ezequiel"
git clone https://github.com/ezebejarano/unqui-po2-bejarano.git Objetos
cd Objetos

# El repo grupal del TP Integrador va ADENTRO, pero es independiente (está en .gitignore)
git clone https://github.com/FuxAgustina/Bejarano-Fux-Gulo-TpIntegradorPO2.git
```

> ⚠️ El nombre de la carpeta importa: tiene que ser exactamente
> `Bejarano-Fux-Gulo-TpIntegradorPO2` para que el `.gitignore` del repo padre la excluya.

## 2. Configurar la identidad de git

En **cada uno** de los dos repos (el TP se corrige por participación individual según los commits,
así que la identidad tiene que estar bien):

```powershell
git config user.name  "ezebejarano"
git config user.email "ezequielbejarano98@gmail.com"
git config --get user.email   # verificar
```

## 3. Instalar el entorno

Ver [SETUP.md](SETUP.md) para el detalle. Mínimo indispensable:

- [ ] **JDK 26** (o ≥ 17) — `java -version` tiene que responder.
- [ ] **Eclipse IDE for Java Developers** — trae JUnit 5 incluido.
- [ ] Abrir Eclipse una vez para que se cree el workspace y bajen los jars a `~/.p2/pool/plugins`.

## 4. Verificar que todo funciona

```powershell
cd "TP integrador"
# (comando completo en el README o en SETUP.md)
java -cp "bin;$jars" TestRunner ar.edu.unq.po2.unqshop
```

Resultado esperado: **15 tests found / 15 tests successful / 0 failed**.

## 5. Importar los proyectos en Eclipse

Los archivos `.project` / `.classpath` **no están versionados** a propósito (son específicos de
cada máquina). En la máquina nueva hay que recrear los proyectos:

1. `File > New > Java Project`, desmarcar *Use default location* y apuntar a la carpeta
   (ej. `Objetos/tp strategy/`, `Objetos/TP integrador/`).
2. Marcar `src/` como source folder y `test/` como source folder de tests.
3. `Build Path > Add Library > JUnit 5`.

## 6. Contexto para Claude Code

La memoria de Claude vive en `%USERPROFILE%\.claude\` y **no viaja con el repo**. Todo el contexto
importante (convenciones, decisiones de diseño, preferencias) está volcado en
[../CLAUDE.md](../CLAUDE.md), que Claude Code lee automáticamente al abrir la carpeta.
No hace falta copiar nada de `.claude/`.

---

## Cosas que se rompen al cambiar de máquina (conocidas)

| Qué | Por qué | Solución |
|---|---|---|
| `Agustina/tps/tp4/src/Supermercado.lnk` | Es un acceso directo de Windows a una ruta de la máquina vieja | Ignorarlo: el código real está en `tp4/src/supermercado/` |
| Proyectos de Eclipse | No hay `.project`/`.classpath` versionados | Recrearlos (paso 5) |
| `.claude/settings.local.json` | Está en `.gitignore` | Se regenera solo con el uso |
| Rutas a los jars de JUnit | Dependen del usuario de Windows | Los comandos usan `$env:USERPROFILE`, se resuelven solos |
