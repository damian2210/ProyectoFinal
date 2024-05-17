package com.example.myapplication.Objetos;

public class ObjPrestar {
    protected PrestarPK prestarPK;

    private Integer cantidad;

    private ObjSucursal sucursal;

    private ObjSucursal sucursalPrest;

    public ObjPrestar(PrestarPK prestarPK, Integer cantidad, ObjSucursal sucursal, ObjSucursal sucursalPrest) {
        this.prestarPK = prestarPK;
        this.cantidad = cantidad;
        this.sucursal = sucursal;
        this.sucursalPrest = sucursalPrest;
    }

    public PrestarPK getPrestarPK() {
        return prestarPK;
    }
    public void setPrestarPK(PrestarPK prestarPK) {
        this.prestarPK = prestarPK;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public ObjSucursal getSucursal() {
        return sucursal;
    }
    public void setSucursal(ObjSucursal sucursal) {
        this.sucursal = sucursal;
    }
    public ObjSucursal getSucursalPrest() {
        return sucursalPrest;
    }
    public void setSucursalPrest(ObjSucursal sucursalPrest) {
        this.sucursalPrest = sucursalPrest;
    }
}
