package com.damian.Servidor.Cliente;


import jakarta.persistence.Basic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private String idCliente;
    @Column(name = "dni")
    private String dni;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Column(name = "num_cuenta")
    private Long numCuenta;


    public String getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public Integer getTelefono() {
        return telefono;
    }
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Long getNumCuenta() {
        return numCuenta;
    }
    public void setNumCuenta(Long numCuenta) {
        this.numCuenta = numCuenta;
    }


}
