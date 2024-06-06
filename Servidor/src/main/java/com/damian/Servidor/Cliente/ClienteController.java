package com.damian.Servidor.Cliente;

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
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
     private final ClienteService cliSer;

    @GetMapping("/listarClientes")
    public List<Cliente> listarClientes() {
        return cliSer.listarClientes();
    }

    @GetMapping("/buscarCliente")
    public Cliente buscarCliente(@RequestParam(value = "id", defaultValue = "0") String id) {
        return cliSer.buscarCliente(id);
    }

    @PostMapping("/insertar")
    public void insertar(@RequestBody Cliente cli) {
        cliSer.insertar(cli);
    }

    @PutMapping("/modificar")
    public void modificar(@RequestBody Cliente cli) {
        cliSer.modificar(cli);
    }

    @DeleteMapping("/borrar")
    public void borrar(@RequestBody Cliente cli) {
        cliSer.borrar(cli);
    }
}
