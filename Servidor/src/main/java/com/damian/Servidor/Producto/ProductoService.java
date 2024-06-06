package com.damian.Servidor.Producto;

import java.util.List;

import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {
private final ProductoRepository proRep;

    public List<Producto> listarProductos() {
        List<Producto> todosLosProductos = proRep.findAll();

        // Filtrar solo los objetos de la clase Vender
        List<Producto> productosFiltrados = todosLosProductos.stream()
                .filter(p -> p instanceof Producto)
                .distinct()
                .toList();
        return productosFiltrados;
    }

    public Producto buscarProducto(String id) {
        return proRep.findAll().stream().filter(p -> p.getCodproducto().equals(id)).findFirst().orElse(null);

    }

    public void insertar(Producto pro) {
        // TODO Auto-generated method stub
        proRep.saveAndFlush(pro);
    }

    public void modificar(Producto pro) {
        // TODO Auto-generated method stub
        Producto producto = proRep.findAll().stream().filter(p -> p.getCodproducto().equals(pro.getCodproducto()))
                .findFirst()
                .orElse(null);
        if (producto != null) {
           
            producto.setFechaDevolucion(pro.getFechaDevolucion());
            producto.setInteres(pro.getInteres());
            producto.setPuntuacion(pro.getPuntuacion());
            producto.setTipo(pro.getTipo());
            proRep.saveAndFlush(producto);
        }
    }

    public void borrar(Producto pro) {
        // TODO Auto-generated method stub
        proRep.delete(pro);
    }

}
