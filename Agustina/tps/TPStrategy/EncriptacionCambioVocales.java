package ar.edu.unq.po2.TPStrategy;

public class EncriptacionCambioVocales implements EstrategiaEncriptacion{
    @Override
    public String encriptar(String texto) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            char letraActual = texto.charAt(i);

            if (letraActual == 'a') { resultado.append("e"); }
            else if (letraActual == 'e') { resultado.append("i"); }
            else if (letraActual == 'i') { resultado.append("o"); }
            else if (letraActual == 'o') { resultado.append("u"); }
            else if (letraActual == 'u') { resultado.append("a"); }
            else {
                resultado.append(letraActual);
            }
        }
        return resultado.toString();
    }

    @Override
    public String desencriptar(String texto) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            char letraActual = texto.charAt(i);

            if (letraActual == 'e') { resultado.append("a"); }
            else if (letraActual == 'i') { resultado.append("e"); }
            else if (letraActual == 'o') { resultado.append("i"); }
            else if (letraActual == 'u') { resultado.append("o"); }
            else if (letraActual == 'a') { resultado.append("u"); }
            else {
                resultado.append(letraActual);
            }
        }
        return resultado.toString();
    }
}


