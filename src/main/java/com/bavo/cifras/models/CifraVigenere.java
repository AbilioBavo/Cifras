package com.bavo.cifras.models;

import java.text.Normalizer;

public class CifraVigenere implements Cifra {
    private String chave;

    public CifraVigenere(String chave) {
        this.chave = chave;
    }

    @Override
    public String cifrar(String texto) {

        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                  .replaceAll("[^a-zA-Z0-9]", "");

        StringBuilder resultado = new StringBuilder();
        int chaveIndex = 0;
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                char chaveChar = chave.charAt(chaveIndex % chave.length());
                char baseChave = Character.isLowerCase(chaveChar) ? 'a' : 'A';
                c = (char) ((c - base + chaveChar - baseChave + 26) % 26 + base);
                chaveIndex++;
            }
            resultado.append(c);
        }
        return resultado.toString();
    }
    
    public String decifrar(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                  .replaceAll("[^a-zA-Z0-9]", "");

        StringBuilder resultado = new StringBuilder();
        int chaveIndex = 0;
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                char chaveChar = chave.charAt(chaveIndex % chave.length());
                char baseChave = Character.isLowerCase(chaveChar) ? 'a' : 'A';
                c = (char) ((c - base - (chaveChar - baseChave) + 26) % 26 + base);
                chaveIndex++;
            }
            resultado.append(c);
        }
        return resultado.toString();
    }
}
