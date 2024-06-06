package com.damian.Servidor.Empleado;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository empRep;

    public List<Empleado> listarEmpleados() {
        List<Empleado> todosLosEmpleados = empRep.findAll();

        // Filtrar solo los objetos de la clase Vender
        List<Empleado> empleadosFiltrados = todosLosEmpleados.stream()
                .filter(e -> e instanceof Empleado)
                .distinct()
                .toList();
        return empleadosFiltrados;
    }

    public Empleado buscarEmpleado(String id) {
        return empRep.findAll().stream().filter(e -> e.getCodEmpleado().equals(id)).findFirst().orElse(null);

    }

    public void insertar(Empleado emp) {
        // TODO Auto-generated method stub
        empRep.saveAndFlush(emp);
    }

    public void modificar(Empleado emp) {
        // TODO Auto-generated method stub
        Empleado empleado = empRep.findAll().stream().filter(e -> e.getCodEmpleado().equals(emp.getCodEmpleado()))
                .findFirst()
                .orElse(null);
        if (empleado != null) {
            empleado.setContraseña(emp.getContraseña());
        
            empleado.setDni(emp.getDni());

            empleado.setRol(emp.getRol());
            empleado.setTelefono(emp.getTelefono());
            empleado.setUsuario(emp.getUsuario());
            empRep.saveAndFlush(empleado);
        }
    }

    public void borrar(Empleado emp) {
        // TODO Auto-generated method stub
        empRep.delete(emp);
    }

    public Empleado buscarUsuario(String usuario,String contraseña) {
        return empRep.findAll().stream().filter(e -> e.getUsuario().equals(usuario)).filter(e -> e.getContraseña().equals(contraseña)).findFirst().orElse(null);

    }

    public Empleado comprobarUsuario(String usuario) {
        return empRep.findAll().stream().filter(e -> e.getUsuario().equals(usuario)).findFirst().orElse(null);

    }
}
