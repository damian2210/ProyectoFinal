package com.damian.Servidor.Prestar;

import java.util.List;

import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrestarService {
    private final PrestarRepository prestarRep;

    public List<Prestar> listarPrestars() {
        List<Prestar> todosLosPrestamos = prestarRep.findAll();

        // Filtrar solo los objetos de la clase Vender
        List<Prestar> prestamosFiltrados = todosLosPrestamos.stream()
                .filter(p -> p instanceof Prestar)
                .distinct()
                .toList();
        return prestamosFiltrados;
    }

    public Prestar buscarPrestamo(String sucRec,String sucPrest) {
        return prestarRep.findAll().stream().filter(p -> p.getPrestarPK().getCodSucursal().equals(sucRec))
        .filter(p -> p.getPrestarPK().getCodSucursalPrestadora().equals(sucPrest)).findFirst().orElse(null);

    }

    public void insertar(Prestar pres) {
        // TODO Auto-generated method stub
        prestarRep.saveAndFlush(pres);
    }

    public void modificar(Prestar pres) {
        // TODO Auto-generated method stub
        Prestar prestar = prestarRep.findAll().stream().filter(p-> p.getPrestarPK().equals(pres.getPrestarPK()))
                .findFirst()
                .orElse(null);
        if (prestar != null) {
            prestar.setCantidad(pres.getCantidad());

            prestarRep.saveAndFlush(prestar);
        }
    }

    public Prestar buscarSucursalEnPrestamo(String sucursal) {
        Prestar pres = prestarRep.findAll().stream().filter(p -> p.getSucursal().getCodSucursal().equals(sucursal)).findFirst().orElse(null);
        if (pres == null) {
            pres = prestarRep.findAll().stream().filter(p -> p.getSucursalPrest().getCodSucursal().equals(sucursal)).findFirst().orElse(null);
        }
        return pres;
    }
}
