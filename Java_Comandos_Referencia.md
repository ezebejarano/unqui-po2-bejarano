# Referencia rápida de comandos Java (POO)

> Machete de los métodos y construcciones que fuimos usando en los TPs.
> Para cada uno: **qué hace · qué devuelve · ejemplo**.

## Índice
1. [Métodos de String](#1-métodos-de-string)
2. [StringBuilder](#2-stringbuilder)
3. [Conversiones (texto ↔ número ↔ char)](#3-conversiones-texto--número--char)
4. [Métodos de Character](#4-métodos-de-character)
5. [Estructuras de control (for)](#5-estructuras-de-control-for)
6. [Otros (println, cast, @Override, ternario)](#6-otros)
7. [Trampas frecuentes](#7-trampas-frecuentes)

---

## 1. Métodos de String

### `split(separador)`
- **Qué hace:** corta el texto cada vez que encuentra el separador.
- **Devuelve:** un array `String[]` con los pedazos.
- **Ejemplo:**
  ```java
  "4,9,5".split(",")        // → ["4", "9", "5"]
  "hola mundo".split(" ")   // → ["hola", "mundo"]
  ```

### `toCharArray()`
- **Qué hace:** convierte el String en un array de caracteres (para recorrerlo con un for).
- **Devuelve:** `char[]`.
- **Ejemplo:**
  ```java
  for (char c : "abc".toCharArray()) { ... }   // recorre 'a', 'b', 'c'
  ```

### `toLowerCase()` / `toUpperCase()`
- **Qué hace:** pasa todo el texto a minúscula / mayúscula.
- **Devuelve:** un `String` nuevo (el original NO se modifica).
- **Ejemplo:**
  ```java
  "Diego".toLowerCase()   // → "diego"
  "hola".toUpperCase()    // → "HOLA"
  ```

### `indexOf(letra)` o `indexOf(texto)`
- **Qué hace:** busca en qué posición aparece la letra/texto.
- **Devuelve:** un `int` con la posición (empezando en 0), o **`-1` si no lo encuentra**.
- **Ejemplo:**
  ```java
  "aeiou".indexOf('a')    // → 0
  "aeiou".indexOf('o')    // → 3
  "aeiou".indexOf('c')    // → -1 (no está)
  ```

### `charAt(posicion)`
- **Qué hace:** devuelve la letra que está en esa posición.
- **Devuelve:** un `char`.
- **Ejemplo:**
  ```java
  "eioua".charAt(0)   // → 'e'
  "eioua".charAt(3)   // → 'u'
  ```

### `length()`
- **Qué hace:** cantidad de caracteres del String.
- **Devuelve:** un `int`.
- **Ejemplo:**
  ```java
  "hola".length()   // → 4
  ```
  > ⚠️ En **String** es `length()` con paréntesis. En **arrays** es `.length` SIN paréntesis (ver Trampas).

### `isEmpty()`
- **Qué hace:** dice si el texto está vacío (`""`).
- **Devuelve:** un `boolean` (`true`/`false`).
- **Ejemplo:**
  ```java
  "".isEmpty()      // → true
  "hola".isEmpty()  // → false
  ```

### `trim()`
- **Qué hace:** saca los espacios sobrantes de los costados (no los del medio).
- **Devuelve:** un `String` nuevo.
- **Ejemplo:**
  ```java
  "  4 ".trim()   // → "4"
  ```

---

## 2. StringBuilder

Sirve para **ir armando un texto de a poco** (es más eficiente que concatenar Strings con `+` dentro de un bucle).

### `new StringBuilder()`
- **Qué hace:** crea un "armador" de texto vacío.
- **Ejemplo:**
  ```java
  StringBuilder sb = new StringBuilder();
  ```

### `append(algo)`
- **Qué hace:** agrega texto/char/número al final.
- **Devuelve:** el mismo `StringBuilder` (se puede encadenar).
- **Ejemplo:**
  ```java
  sb.append("hola");
  sb.append(' ');
  sb.append(4);        // también acepta números
  ```

### `length()`
- **Qué hace:** cuántos caracteres lleva acumulados.
- **Devuelve:** un `int`. Útil para saber si ya hay algo escrito.
- **Ejemplo:**
  ```java
  if (sb.length() > 0) { sb.append(","); }   // pone coma solo si NO es el primero
  ```

### `toString()`
- **Qué hace:** convierte lo acumulado en un `String` final.
- **Devuelve:** un `String`.
- **Ejemplo:**
  ```java
  return sb.toString();
  ```

---

## 3. Conversiones (texto ↔ número ↔ char)

### `Integer.parseInt(texto)`
- **Qué hace:** convierte un texto que representa un número en un número de verdad.
- **Devuelve:** un `int`. (Si el texto no es un número válido, lanza error `NumberFormatException`.)
- **Ejemplo:**
  ```java
  Integer.parseInt("4")    // → 4 (el número, no el texto)
  Integer.parseInt("15")   // → 15
  ```

### Aritmética con `char` (letra → número)
- **Qué hace:** un `char` es internamente un número, así que se puede restar/sumar. `c - 'a'` da la distancia entre letras.
- **Devuelve:** un `int`.
- **Ejemplo:**
  ```java
  'a' - 'a'        // → 0
  'd' - 'a'        // → 3
  'd' - 'a' + 1    // → 4   (para que la 'a' valga 1)
  ```

### Cast `(char)` (número → letra)
- **Qué hace:** convierte un número de vuelta a su carácter.
- **Devuelve:** un `char`.
- **Ejemplo:**
  ```java
  (char) ('a' + 3)        // → 'd'
  (char) ('a' + 4 - 1)    // → 'd'  (desde el número 4)
  ```
  > Sin el `(char)`, `'a' + 3` daría el número `100`, no la letra.

---

## 4. Métodos de Character

### `Character.toLowerCase(c)` / `Character.toUpperCase(c)`
- **Qué hace:** pasa UN carácter a minúscula / mayúscula.
- **Devuelve:** un `char`.
- **Ejemplo:**
  ```java
  Character.toLowerCase('A')   // → 'a'
  Character.toUpperCase('e')   // → 'E'
  ```

### `Character.isUpperCase(c)`
- **Qué hace:** dice si el carácter es mayúscula.
- **Devuelve:** un `boolean`.
- **Ejemplo:**
  ```java
  Character.isUpperCase('A')   // → true
  Character.isUpperCase('a')   // → false
  ```
  > Hay parecidos: `isLowerCase(c)`, `isDigit(c)` (¿es número?), `isLetter(c)` (¿es letra?).

---

## 5. Estructuras de control (for)

### `for-each` (recorrer una colección)
- **Qué hace:** recorre cada elemento de un array/lista, sin manejar índices.
- **Ejemplo:**
  ```java
  for (char c : texto.toCharArray()) { ... }   // c toma cada letra
  for (String parte : texto.split(",")) { ... }
  ```

### `for` clásico (con índice)
- **Qué hace:** recorre con un contador. Sirve cuando necesitás la posición o ir **para atrás**.
- **Ejemplo (hacia adelante):**
  ```java
  for (int i = 0; i < palabras.length; i++) { ... }
  ```
- **Ejemplo (hacia atrás, como en OrdenPalabras):**
  ```java
  for (int i = palabras.length - 1; i >= 0; i--) { ... }
  // empieza en la última posición y resta de a 1
  ```

---

## 6. Otros

### `System.out.println(...)`
- **Qué hace:** imprime en la consola y baja un renglón.
- **Devuelve:** nada (`void`).
- **Ejemplo:**
  ```java
  System.out.println("Resultado: " + resultado);
  ```

### Operador ternario `condición ? a : b`
- **Qué hace:** es un `if/else` corto: si la condición es verdadera da `a`, si no da `b`.
- **Ejemplo (equivalentes):**
  ```java
  int n = (c == ' ') ? 0 : c - 'a' + 1;
  // es lo mismo que:
  int n;
  if (c == ' ') { n = 0; } else { n = c - 'a' + 1; }
  ```

### `@Override`
- **Qué hace:** marca que un método implementa/sobrescribe uno de una interfaz o superclase. **Opcional** pero recomendado: si te equivocás en la firma, el compilador te avisa.
- **Ejemplo:**
  ```java
  @Override
  public String encriptar(String texto) { ... }
  ```

---

## 7. Trampas frecuentes

| Confusión | Correcto |
|---|---|
| Tamaño de un **String** | `texto.length()` ← **con** paréntesis (es método) |
| Tamaño de un **array** | `palabras.length` ← **sin** paréntesis (es atributo) |
| Tamaño de una **lista** (`List`) | `lista.size()` |
| `"4"` (texto) vs `4` (número) | usá `Integer.parseInt("4")` para pasar de uno a otro |
| `'a'` (char, comillas simples) vs `"a"` (String, comillas dobles) | son tipos distintos |
| `==` con Strings | para comparar texto usá `texto.equals(otro)`, NO `==` |
| Las posiciones (índices) | empiezan en **0**, no en 1. El último es `length - 1` |
