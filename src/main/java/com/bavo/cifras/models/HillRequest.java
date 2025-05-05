package com.bavo.cifras.models;

import java.util.List;

public class HillRequest {
    private String texto;
    private List<List<Integer>> matriz; // Matriz representada como uma lista de listas de inteiros

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public List<List<Integer>> getMatriz() {
        return matriz;
    }

    public void setMatriz(List<List<Integer>> matriz) {
        this.matriz = matriz;
    }

    public int[][] getMatrizAsArray() {
        // Converte a matriz de List<List<Integer>> para int[][]
        int tamanho = matriz.size();
        int[][] array = new int[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                array[i][j] = matriz.get(i).get(j);
            }
        }
        return array;
    }
}