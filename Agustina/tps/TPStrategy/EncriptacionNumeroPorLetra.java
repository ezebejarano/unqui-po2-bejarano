package ar.edu.unq.po2.TPStrategy;

public class EncriptacionNumeroPorLetra implements EstrategiaEncriptacion{
    private String abecedario = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public String encriptar(String texto) {
        StringBuilder resultado = new StringBuilder();
        String textoMinuscula = texto.toLowerCase();

        for (int i = 0; i < textoMinuscula.length(); i++) {
            char letraActual = textoMinuscula.charAt(i);

            if (letraActual == ' ') {
                resultado.append("0");
            } else {
                // .indexOf nos dice en qué posición del abecedario está la letra.
                // Como para nosotros la 'a' tiene que ser 1 (y en Java es 0), le sumamos 1.
                int numero = abecedario.indexOf(letraActual) + 1;
                resultado.append(numero);
            }

            // Si no es la última letra, le pegamos la coma al final
            if (i < textoMinuscula.length() - 1) {
                resultado.append(",");
            }
        }
        return resultado.toString();
    }

    @Override
    public String desencriptar(String texto) {
        StringBuilder resultado = new StringBuilder();

        // El .split(",") corta el texto "4,9,5,7,15" en un listado de textos sueltos: ["4", "9", "5", "7", "15"]
        String[] numerosEnTexto = texto.split(",");

        for (int i = 0; i < numerosEnTexto.length; i++) {
            // Pasamos el texto "4" a un número entero de verdad (4)
            int numero = Integer.parseInt(numerosEnTexto[i]);

            if (numero == 0) {
                resultado.append(" ");
            } else {
                // Buscamos qué letra está en esa posición del abecedario.
                // Restamos 1 porque nuestro código venía sumado.
                char letraEncontrada = abecedario.charAt(numero - 1);
                resultado.append(letraEncontrada);
            }
        }
        return resultado.toString();
    }
}


