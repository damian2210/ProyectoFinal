package com.damian.Servidor.Vender;

import java.util.List;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenderService {
    private final VenderRepository venderRep;

    public List<Vender> listarVentas() {
        List<Vender> todasLasVentas = venderRep.findAll();

        // Filtrar solo los objetos de la clase Vender
        List<Vender> ventasFiltradas = todasLasVentas.stream()
                .filter(v -> v instanceof Vender)
                .distinct()
                .toList();
        return ventasFiltradas;
    }

    public Vender buscarVenta(String codProd,String idCliente) {
        return venderRep.findAll().stream().filter(v -> v.getVenderPK().getCodproducto().equals(codProd))
        .filter(v -> v.getVenderPK().getIdCliente().equals(idCliente) )
        .findFirst().orElse(null);

    }

    public Vender buscarProductoEnVenta(String id) {
        // TODO Auto-generated method stub
        return venderRep.findAll().stream().filter(v -> v.getProductoFinanciero().getCodproducto().equals(id)).findFirst().orElse(null);
    }

    public Vender buscarClienteEnVenta(String id) {
        // TODO Auto-generated method stub
        return venderRep.findAll().stream().filter(v -> v.getCliente().getIdCliente().equals(id)).findFirst().orElse(null);
    }

    public void insertar(Vender venta) {
        // TODO Auto-generated method stub
        venderRep.saveAndFlush(venta);
    }

    public void modificar(Vender venta) {
        // TODO Auto-generated method stub
        Vender vender = venderRep.findAll().stream().filter(v -> v.getVenderPK() == venta.getVenderPK()).findFirst()
                .orElse(null);
        if (vender != null) {
            vender.setCodEmpleado(venta.getCodEmpleado());
            venderRep.saveAndFlush(vender);
        }
    }

    public void modificarEmpNull(Vender venta) {
        // TODO Auto-generated method stub
        Vender vender = venderRep.findAll().stream().filter(v -> v.getVenderPK() == venta.getVenderPK()).findFirst()
                .orElse(null);
        if (vender != null) {
            vender.setCodEmpleado(null);
            venderRep.save(vender);
        }
    }

}
