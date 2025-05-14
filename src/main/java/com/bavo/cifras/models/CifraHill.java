package com.bavo.cifras.models;

import java.text.Normalizer;

public class CifraHill implements Cifra {
    private int[][] chave;
    private int tamanho;

    public CifraHill(int[][] chave) {
        this.chave = chave;
        this.tamanho = chave.length;
    }

    @Override
    public String cifrar(String texto) {
          int determinante = calcularDeterminante(chave);
        if (determinante == 0) {
            return "Erro: O determinante da matriz é 0. Escolha uma matriz válida.";
        }
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                          .replaceAll("[^a-zA-Z]", "")
                          .toUpperCase();

        while (texto.length() % tamanho != 0) {
            texto += "X";
        }

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i += tamanho) {
            int[] bloco = new int[tamanho];
            for (int j = 0; j < tamanho; j++) {
                bloco[j] = letraParaNumero(texto.charAt(i + j));
            }

            int[] cifrado = multiplicarMatriz(chave, bloco);

            for (int valor : cifrado) {
                resultado.append(numeroParaLetra(valor));
            }
        }

        return resultado.toString();
    }

    public String decifrar(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                          .replaceAll("[^a-zA-Z]", "")
                          .toUpperCase();

        StringBuilder resultado = new StringBuilder();
        int[][] inversa = calcularInversa(chave);

        for (int i = 0; i < texto.length(); i += tamanho) {
            int[] bloco = new int[tamanho];
            for (int j = 0; j < tamanho; j++) {
                bloco[j] = letraParaNumero(texto.charAt(i + j));
            }

            int[] decifrado = multiplicarMatriz(inversa, bloco);

            for (int valor : decifrado) {
                resultado.append(numeroParaLetra(valor));
            }
        }

        return resultado.toString();
    }

    private int[] multiplicarMatriz(int[][] matriz, int[] vetor) {
        int[] resultado = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                resultado[i] += matriz[i][j] * vetor[j];
            }
            resultado[i] = ((resultado[i] % 26) + 26) % 26;
        }
        return resultado;
    }

    private int[][] calcularInversa(int[][] matriz) {
        int determinante = calcularDeterminante(matriz);
        int inversoDet = inversoModular(determinante, 26);

        int[][] adjunta = calcularAdjunta(matriz);
        int[][] inversa = new int[tamanho][tamanho];

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                inversa[i][j] = (adjunta[i][j] * inversoDet) % 26;
                if (inversa[i][j] < 0) {
                    inversa[i][j] += 26;
                }
            }
        }

        return inversa;
    }

    private int calcularDeterminante(int[][] matriz) {
        if (tamanho == 2) {
            return (matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0]) % 26;
        }
        throw new UnsupportedOperationException("Determinante para matrizes maiores que 2x2 não implementado.");
    }

    private int inversoModular(int a, int m) {
        a = ((a % m) + m) % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        throw new ArithmeticException("Inverso modular não existe.");
    }

    private int[][] calcularAdjunta(int[][] matriz) {
        if (tamanho == 2) {
            return new int[][] {
                { matriz[1][1], -matriz[0][1] },
                { -matriz[1][0], matriz[0][0] }
            };
        }
        throw new UnsupportedOperationException("Adjunta para matrizes maiores que 2x2 não implementado.");
    }

    // Função de conversão letra → número (A=1, ..., Z=0)
    private int letraParaNumero(char letra) {
        int valor = (letra - 'A' + 1) % 26;
        return valor;
    }

    // Função de conversão número → letra (0=Z, 1=A, ..., 25=Y)
    private char numeroParaLetra(int valor) {
        if (valor == 0) return 'Z';
        return (char) ((valor - 1) + 'A');
    }
}
