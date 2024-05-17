package com.damian.Servidor.Sucursal;

import java.util.List;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalService {
    private final SucursalRepository sucRep;

    public List<Sucursal> listarSucursales() {
        List<Sucursal> todosLasSucursales = sucRep.findAll();

        // Filtrar solo los objetos de la clase Vender
        List<Sucursal> sucursalesFiltrados = todosLasSucursales.stream()
                .filter(s -> s instanceof Sucursal)
                .distinct()
                .toList();
        return sucursalesFiltrados;
    }

    public Sucursal buscarSucursal(String id) {
        return sucRep.findAll().stream().filter(s -> s.getCodSucursal().equals(id)).findFirst().orElse(null);

    }

    public void insertar(Sucursal suc) {
        // TODO Auto-generated method stub
        sucRep.saveAndFlush(suc);
    }

    public void modificar(Sucursal suc) {
        // TODO Auto-generated method stub
        Sucursal sucursal = sucRep.findAll().stream().filter(s -> s.getCodSucursal().equals(suc.getCodSucursal()))
                .findFirst()
                .orElse(null);
        if (sucursal != null) {
            sucursal.setTelefono(suc.getTelefono());
            sucursal.setDireccion(suc.getDireccion());

            sucRep.saveAndFlush(sucursal);
        }
    }

    public void borrar(Sucursal suc) {
        // TODO Auto-generated method stub
        sucRep.delete(suc);
    }

    
}
