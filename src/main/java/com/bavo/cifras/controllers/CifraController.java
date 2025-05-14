package com.bavo.cifras.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bavo.cifras.models.Cifra;
import com.bavo.cifras.models.CifraCesar;
import com.bavo.cifras.models.CifraHill;
import com.bavo.cifras.models.CifraResquest;
import com.bavo.cifras.models.CifraVigenere;
import com.bavo.cifras.models.HillRequest;

@Controller
@RequestMapping("/cifras")
public class CifraController {
    
    @PostMapping("/cifrar")
    public ResponseEntity<String> cifrarTexto(@RequestBody CifraResquest request) {
        String resultado = null;
        if ("cesar".equalsIgnoreCase(request.getTipo())) {
            // Validação: apenas inteiros positivos
            try {
                int chave = Integer.parseInt(request.getChave());
                if (chave <= 0) {
                    return ResponseEntity.badRequest().body("A chave da cifra de César deve ser um número inteiro positivo.");
                }
                Cifra cifra = new CifraCesar(chave);
                resultado = cifra.cifrar(request.getTexto());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("A chave da cifra de César deve ser um número inteiro positivo.");
            }
        } else if ("vigenere".equalsIgnoreCase(request.getTipo())) {
            // Validação: apenas letras
            if (!request.getChave().matches("^[a-zA-Z]+$")) {
                return ResponseEntity.badRequest().body("A chave da cifra de Vigenère deve conter apenas letras (A-Z, a-z).");
            }
            Cifra cifra = new CifraVigenere(request.getChave());
            resultado = cifra.cifrar(request.getTexto());
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/decifrar")
    public ResponseEntity<String> decifrarTexto(@RequestBody CifraResquest request) {
        String resultado = null;
        if ("cesar".equalsIgnoreCase(request.getTipo())) {
            // Validação: apenas inteiros positivos
            try {
                int chave = Integer.parseInt(request.getChave());
                if (chave <= 0) {
                    return ResponseEntity.badRequest().body("A chave da cifra de César deve ser um número inteiro positivo.");
                }
                Cifra cifra = new CifraCesar(-chave);
                resultado = cifra.cifrar(request.getTexto());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("A chave da cifra de César deve ser um número inteiro positivo.");
            }
        } else if ("vigenere".equalsIgnoreCase(request.getTipo())) {
            // Validação: apenas letras
            if (!request.getChave().matches("^[a-zA-Z]+$")) {
                return ResponseEntity.badRequest().body("A chave da cifra de Vigenère deve conter apenas letras (A-Z, a-z).");
            }
            Cifra cifra = new CifraVigenere(request.getChave());
            resultado = cifra.decifrar(request.getTexto());
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/cifrarHill")
    public ResponseEntity<String> cifrarHill(@RequestBody HillRequest request) {
        int[][] matriz = request.getMatrizAsArray();
        // Validação: todos os elementos devem ser inteiros (já garantido pelo tipo), mas vamos garantir não nulos
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                // Se algum valor for "null" (não convertido corretamente), lança erro
                if (matriz[i][j] != matriz[i][j]) {
                    return ResponseEntity.badRequest().body("Todos os campos da matriz Hill devem ser preenchidos com números inteiros.");
                }
            }
        }
        Cifra cifra = new CifraHill(matriz);
        String resultado = cifra.cifrar(request.getTexto());
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/decifrarHill")
    public ResponseEntity<String> decifrarHill(@RequestBody HillRequest request) {
        int[][] matriz = request.getMatrizAsArray();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] != matriz[i][j]) {
                    return ResponseEntity.badRequest().body("Todos os campos da matriz Hill devem ser preenchidos com números inteiros.");
                }
            }
        }
        Cifra cifra = new CifraHill(matriz);
        String resultado = cifra.decifrar(request.getTexto());
        return ResponseEntity.ok(resultado);
    }
}
