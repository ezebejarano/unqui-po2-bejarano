package ar.edu.unq.po2.TPStrategy;

public class EncriptacionCambioOrdenPalabras implements EstrategiaEncriptacion {
    @Override
    public String encriptar(String texto) {
        if (texto.isEmpty()) return texto;
        String[] palabras = texto.split(" ");
        StringBuilder resultado = new StringBuilder();

        for (int i = palabras.length - 1; i >= 0; i--) {
            resultado.append(palabras[i]);
            if (i > 0) resultado.append(" ");
        }
        return resultado.toString();
    }

    @Override
    public String desencriptar(String texto) {
        return this.encriptar(texto);
    }
}
