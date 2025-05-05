package com.bavo.cifras.models;

import java.text.Normalizer;

public class CifraCesar implements Cifra {
    private int chave;

    public CifraCesar(int chave) {
        this.chave = chave;
    }

    @Override
    public String cifrar(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                  .replaceAll("[^a-zA-Z0-9]", "");

        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base + chave) % 26 + base);
            }
            resultado.append(c);
        }
        return resultado.toString();
    }

    @Override
    public String decifrar(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                  .replaceAll("[^a-zA-Z0-9]", "");
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base - chave + 26) % 26 + base); // Subtraímos a chave para decifrar
            }
            resultado.append(c);
        }
        return resultado.toString();
    }
}