package com.example.myapplication.Objetos;

public class ObjCliente {
    private String idCliente;

    private String dni;

    private Integer telefono;

    private String nombre;

    private Long numCuenta;

    public ObjCliente(String idCliente, String dni, Integer telefono, String nombre, Long numCuenta) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.telefono = telefono;
        this.nombre = nombre;
        this.numCuenta = numCuenta;
    }

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
