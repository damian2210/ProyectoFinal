package com.damian.Servidor.Cliente;

import java.util.List;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository cliRep;

    public List<Cliente> listarClientes() {
        List<Cliente> todosLosClientes = cliRep.findAll();

        // Filtrar solo los objetos de la clase Vender
        List<Cliente> clientesFiltrados = todosLosClientes.stream()
                .filter(c -> c instanceof Cliente)
                .distinct()
                .toList();
        return clientesFiltrados;
    }

    public Cliente buscarCliente(String id) {
        return cliRep.findAll().stream().filter(c -> c.getIdCliente().equals(id)).findFirst().orElse(null);

    }

    public void insertar(Cliente cli) {
        // TODO Auto-generated method stub
        cliRep.saveAndFlush(cli);
    }

    public void modificar(Cliente cli) {
        // TODO Auto-generated method stub
        Cliente cliente = cliRep.findAll().stream().filter(c -> c.getIdCliente().equals(cli.getIdCliente()))
                .findFirst()
                .orElse(null);
        if (cliente != null) {

            cliente.setDni(cli.getDni());
            cliente.setNombre(cli.getNombre());
            cliente.setNumCuenta(cli.getNumCuenta());
            cliente.setTelefono(cli.getTelefono());
       
            cliRep.saveAndFlush(cliente);
        }
    }

    public void borrar(Cliente cli) {
        // TODO Auto-generated method stub
        cliRep.delete(cli);
    }

}
