package com.example.myapplication.Objetos;

public class PrestarPK {

    private String codSucursal;

    private String codSucursalPrestadora;

    public PrestarPK(String codSucursal, String codSucursalPrestadora) {
        this.codSucursal = codSucursal;
        this.codSucursalPrestadora = codSucursalPrestadora;
    }

    public String getCodSucursal() {
        return codSucursal;
    }

    public void setCodSucursal(String codSucursal) {
        this.codSucursal = codSucursal;
    }

    public String getCodSucursalPrestadora() {
        return codSucursalPrestadora;
    }

    public void setCodSucursalPrestadora(String codSucursalPrestadora) {
        this.codSucursalPrestadora = codSucursalPrestadora;
    }
}
