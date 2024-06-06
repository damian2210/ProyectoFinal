package com.damian.Servidor.Producto;

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
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService proSer;

    @GetMapping("/listarProductos")
    public List<Producto> listarProductos() {
        return proSer.listarProductos();
    }

    @GetMapping("/buscarProducto")
    public Producto buscarProducto(@RequestParam(value = "id", defaultValue = "0") String id) {
        return proSer.buscarProducto(id);
    }

    @PostMapping("/insertar")
    public void insertar(@RequestBody Producto pro) {
        proSer.insertar(pro);
    }

    @PutMapping("/modificar")
    public void modificar(@RequestBody Producto pro) {
        proSer.modificar(pro);
    }

    @DeleteMapping("/borrar")
    public void borrar(@RequestBody Producto pro) {
        proSer.borrar(pro);
    }
}
