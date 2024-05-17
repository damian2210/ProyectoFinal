package com.damian.Servidor.Sucursal;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sucursal")
@RequiredArgsConstructor
public class SucursalController {
     private final SucursalService sucSer;

    @GetMapping("/listarSucursales")
    public List<Sucursal> listarSucursales() {
        return sucSer.listarSucursales();
    }

    @GetMapping("/buscarSucursal")
    public Sucursal buscarSucursal(@RequestParam(value = "id", defaultValue = "0") String id) {
        return sucSer.buscarSucursal(id);
    }

    @PostMapping("/insertar")
    public void insertar(@RequestBody Sucursal suc) {
        sucSer.insertar(suc);
    }

    @PutMapping("/modificar")
    public void modificar(@RequestBody Sucursal suc) {
        sucSer.modificar(suc);
    }

    @DeleteMapping("/borrar")
    public void borrar(@RequestBody Sucursal suc) {
        sucSer.borrar(suc);
    }
}
