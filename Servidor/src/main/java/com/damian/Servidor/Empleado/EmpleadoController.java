package com.damian.Servidor.Empleado;

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
@RequestMapping("/empleado")
@RequiredArgsConstructor
public class EmpleadoController {
    private final EmpleadoService empSer;

    @GetMapping("/listarEmpleados")
    public List<Empleado> listarEmpleados() {
        return empSer.listarEmpleados();
    }

    @GetMapping("/buscarEmpleado")
    public Empleado buscarEmpleado(@RequestParam(value = "id", defaultValue = "0") String id) {
        return empSer.buscarEmpleado(id);
    }

    @GetMapping("/buscarUsuario")
    public Empleado buscarUsuario(@RequestParam(value = "usuario", defaultValue = "0") String usuario,@RequestParam(value = "contraseña", defaultValue = "0") String contraseña) {
        return empSer.buscarUsuario(usuario,contraseña);
    }
    @GetMapping("/comprobarUsuario")
    public Empleado comprobarUsuario(@RequestParam(value = "usuario", defaultValue = "0") String usuario) {
        return empSer.comprobarUsuario(usuario);
    }

    @PostMapping("/insertar")
    public void insertar(@RequestBody Empleado emp) {
        empSer.insertar(emp);
    }

    @PutMapping("/modificar")
    public void modificar(@RequestBody Empleado emp) {
        empSer.modificar(emp);
    }

    @DeleteMapping("/borrar")
    public void borrar(@RequestBody Empleado emp) {
        empSer.borrar(emp);
    }
}
