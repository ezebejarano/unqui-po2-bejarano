//1. Contador de pares, impares y múltiplos

package ar.edu.unq.po2.tp3;

import java.util.ArrayList;

public class Counter {
    private ArrayList<Integer> numeros = new ArrayList<Integer>();


    public void addNumber(int numero) {
        this.numeros.add(numero);
    }

    public int getCantidadDePares() {
        int contador = 0;
        for (Integer n : numeros) {
            if (n % 2 == 0) {
                contador++;
            }
        }
        return contador;
    }

    public int getCantidadImpares() {
        int cantidad = 0;
        for (Integer n : numeros) {
            if (n % 2 != 0) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int getMultiplosDe(int multiplo) {
        int contador = 0;
        for (int n : numeros) {
            if (n % multiplo == 0) {
                contador++;
            }
        }
        return contador;
    }
}

//2. Desarmando números

//Diseñe e implemente una función Java que reciba un arreglo de números enteros y
//devuelva el número que tiene la mayor cantidad de dígitos pares. No puede utilizar
//String!!. Por lo cual, debe razonar cómo desarmar el número utilizando los operadores
//de los números enteros (div y mod)

