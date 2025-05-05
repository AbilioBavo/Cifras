package com.bavo.cifras.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CifraResquest {
    private String operacao;
    private String texto;
    private String tipo;
    private String chave;
}
