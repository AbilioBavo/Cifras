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
            Cifra cifra = new CifraCesar(Integer.parseInt(request.getChave()));
            resultado = cifra.cifrar(request.getTexto());
        } else if ("vigenere".equalsIgnoreCase(request.getTipo())) {
            Cifra cifra = new CifraVigenere(request.getChave());
            resultado = cifra.cifrar(request.getTexto());
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/decifrar")
    public ResponseEntity<String> decifrarTexto(@RequestBody CifraResquest request) {
        String resultado = null;
        if ("cesar".equalsIgnoreCase(request.getTipo())) {
            Cifra cifra = new CifraCesar(-Integer.parseInt(request.getChave()));
            resultado = cifra.cifrar(request.getTexto());
        } else if ("vigenere".equalsIgnoreCase(request.getTipo())) {
            Cifra cifra = new CifraVigenere(request.getChave());
            resultado = cifra.decifrar(request.getTexto());
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/cifrarHill")
    public ResponseEntity<String> cifrarHill(@RequestBody HillRequest request) {
        int[][] matriz = request.getMatrizAsArray(); // Converter a matriz para int[][]
        Cifra cifra = new CifraHill(matriz);
        String resultado = cifra.cifrar(request.getTexto());
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/decifrarHill")
    public ResponseEntity<String> decifrarHill(@RequestBody HillRequest request) {
        int[][] matriz = request.getMatrizAsArray(); // Converter a matriz para int[][]
        Cifra cifra = new CifraHill(matriz);
        String resultado = cifra.decifrar(request.getTexto());
        return ResponseEntity.ok(resultado);
    }
}
