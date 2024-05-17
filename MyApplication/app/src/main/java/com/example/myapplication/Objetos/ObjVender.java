package com.example.myapplication.Objetos;

import java.sql.Date;

public class ObjVender {
    protected VenderPK venderPK;

    private Date fechaVenta;

    private ObjCliente cliente;

    private ObjEmpleado codEmpleado;

    private ObjProducto productoFinanciero;


    public ObjVender(VenderPK venderPK, Date fechaVenta, ObjCliente cliente, ObjEmpleado codEmpleado, ObjProducto productoFinanciero) {
        this.venderPK = venderPK;
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        this.codEmpleado = codEmpleado;
        this.productoFinanciero = productoFinanciero;
    }

    public VenderPK getVenderPK() {
        return venderPK;
    }



    public void setVenderPK(VenderPK venderPK) {
        this.venderPK = venderPK;
    }



    public Date getFechaVenta() {
        return fechaVenta;
    }



    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }



    public ObjCliente getCliente() {
        return cliente;
    }



    public void setCliente(ObjCliente cliente) {
        this.cliente = cliente;
    }



    public ObjEmpleado getCodEmpleado() {
        return codEmpleado;
    }



    public void setCodEmpleado(ObjEmpleado codEmpleado) {
        this.codEmpleado = codEmpleado;
    }



    public ObjProducto getProductoFinanciero() {
        return productoFinanciero;
    }



    public void setProductoFinanciero(ObjProducto productoFinanciero) {
        this.productoFinanciero = productoFinanciero;
    }
}
