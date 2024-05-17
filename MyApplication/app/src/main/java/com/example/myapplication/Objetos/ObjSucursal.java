package com.example.myapplication.Objetos;

public class ObjSucursal {
    private String codSucursal;

    private String direccion;

    private Integer telefono;

    public ObjSucursal(String codSucursal, String direccion, Integer telefono) {
        this.codSucursal = codSucursal;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getCodSucursal() {
        return codSucursal;
    }
    public void setCodSucursal(String codSucursal) {
        this.codSucursal = codSucursal;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public Integer getTelefono() {
        return telefono;
    }
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
}
