package com.damian.Servidor.Prestar;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/prestar")
@RequiredArgsConstructor
public class PrestarController {
    private final PrestarService prestarSer;

    @GetMapping("/listarPrestars")
    public List<Prestar> listarPrestars() {
        return prestarSer.listarPrestars();
    }

    @GetMapping("/buscarPrestamo")
    public Prestar buscarPrestamo(@RequestParam(value = "id", defaultValue = "0") String sucRec,@RequestParam(value = "id2", defaultValue = "0") String sucPrest) {
        return prestarSer.buscarPrestamo(sucRec,sucPrest);
    }

    @GetMapping("/buscarSucursalEnPrestamo")
    public Prestar buscarSucursalEnPrestamo(@RequestParam(value = "id", defaultValue = "0") String id) {
        return prestarSer.buscarSucursalEnPrestamo(id);
    }

    @PostMapping("/insertar")
    public void insertar(@RequestBody Prestar pres) {
        prestarSer.insertar(pres);
    }

    @PutMapping("/modificar")
    public void modificar(@RequestBody Prestar pres) {
        prestarSer.modificar(pres);
    }

    
}
